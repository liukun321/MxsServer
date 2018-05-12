package com.mxs.mxsserver.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxs.mxsserver.handler.KeepAliveRequestHandle;
import com.mxs.mxsserver.handler.LoginRequestHandler;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.handler.coffee.AddStockHandler;
import com.mxs.mxsserver.handler.coffee.CancelTradeHandler;
import com.mxs.mxsserver.handler.coffee.GetAdvPicsRequestHandler;
import com.mxs.mxsserver.handler.coffee.GetCoffeeRequestHandler;
import com.mxs.mxsserver.handler.coffee.GetCouponsRequestHandler;
import com.mxs.mxsserver.handler.coffee.GetDosingListRequestHandler;
import com.mxs.mxsserver.handler.coffee.GetMachineConfigRequestHandler;
import com.mxs.mxsserver.handler.coffee.MachineStatusReportRequestHandler;
import com.mxs.mxsserver.handler.coffee.PayBarCodeRequestHandler;
import com.mxs.mxsserver.handler.coffee.RollbackOrderRequestHandler;
import com.mxs.mxsserver.handler.coffee.UpdateMachineAddressHandler;
import com.mxs.mxsserver.handler.coffee.UpdateStockHandler;
import com.mxs.mxsserver.protocol.LinkFrame;
import com.mxs.mxsserver.protocol.request.KeepAliveRequest;
import com.mxs.mxsserver.protocol.request.LoginRequest;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.coffee.AddStockRequest;
import com.mxs.mxsserver.protocol.request.coffee.CancelTradeRequest;
import com.mxs.mxsserver.protocol.request.coffee.GetAdvPicRequest;
import com.mxs.mxsserver.protocol.request.coffee.GetCoffeeRequest;
import com.mxs.mxsserver.protocol.request.coffee.GetCouponsRequest;
import com.mxs.mxsserver.protocol.request.coffee.GetDosingListRequest;
import com.mxs.mxsserver.protocol.request.coffee.GetMachineConfigRequest;
import com.mxs.mxsserver.protocol.request.coffee.MachineStatusReportRequest;
import com.mxs.mxsserver.protocol.request.coffee.PayBarCodeRequest;
import com.mxs.mxsserver.protocol.request.coffee.RollbackOrderRequest;
import com.mxs.mxsserver.protocol.request.coffee.UpdateMachineAddressRequest;
import com.mxs.mxsserver.protocol.request.coffee.UpdateStockRequest;

public class RequestFactory {
	private final Logger log = LoggerFactory.getLogger(RequestFactory.class);
	private static RequestFactory sInstance = new RequestFactory();

	public static RequestFactory getInstance() {
		return sInstance;
	}

	private Map<Pair<Short, Short>, Class<? extends Request>> mRequests = new ConcurrentHashMap<Pair<Short, Short>, Class<? extends Request>>();
	private Map<Class<? extends Request>, RequestHandler> mHandlers = new ConcurrentHashMap<Class<? extends Request>, RequestHandler>();
	private Map<Pair<Short, Short>, Integer> mPriorities = new ConcurrentHashMap<Pair<Short, Short>, Integer>();

	private RequestFactory() {
		registerrequest();
	}

	private void registerrequest() {
		//登陆请求
		registerRequest(LoginRequest.class, new LoginRequestHandler());
		registerRequest(GetDosingListRequest.class, new GetDosingListRequestHandler());
		registerRequest(UpdateStockRequest.class, new UpdateStockHandler());
		//获取咖啡机配置信息
		registerRequest(GetMachineConfigRequest.class, new GetMachineConfigRequestHandler());
		//获取首页请求
		registerRequest(GetAdvPicRequest.class, new GetAdvPicsRequestHandler());
		//获取咖啡 列表请求
		registerRequest(GetCoffeeRequest.class, new GetCoffeeRequestHandler());
		//添加物料，初始化物料请求
		registerRequest(AddStockRequest.class, new AddStockHandler());
//		registerRequest(PayQrcodeCartRequest.class, new PayQrcodeCartRequestHandler());
//		registerRequest(PayStatusAskCartRequest.class, new PayStatusCartRequestHandler());
		//咖啡机状态报告请求
		registerRequest(MachineStatusReportRequest.class, new MachineStatusReportRequestHandler());
		//退款请求
		registerRequest(RollbackOrderRequest.class, new RollbackOrderRequestHandler());
//		registerRequest(AppDownloadRequest.class, new AppDownloadRequestHandler());
		//取消订单
		registerRequest(CancelTradeRequest.class, new CancelTradeHandler());
//		registerRequest(GetDiscountRequest.class, new GetDicountRequestHandler());
//		registerRequest(MachineStatusServerRequest.class, new MachineStatusServerResquestHandler());
//		registerRequest(PayQrcodeRequest.class, new PayQrcodeRequestHandler());
		
//		registerRequest(RollbackRequest.class, new RollbackIndentRequestHandler());
//		registerRequest(VerifyCoffeeRequest.class, new VerifyCoffeeRequestHandler());
		//保持会话的请求
		registerRequest(KeepAliveRequest.class, new KeepAliveRequestHandle());
		//条形码支付的请求
		registerRequest(PayBarCodeRequest.class, new PayBarCodeRequestHandler());
		//获取优惠券的请求
		registerRequest(GetCouponsRequest.class, new GetCouponsRequestHandler());
		//更新咖啡机地理位置信息
		registerRequest(UpdateMachineAddressRequest.class, new UpdateMachineAddressHandler());
	}

	private void registerRequest(Class<? extends Request> clazz, RequestHandler handler) {
		// TODO Auto-generated method stub
		RequestID annotation = (RequestID) clazz.getAnnotation(RequestID.class);
		if (annotation == null) {
			System.out.println("null annotation ");
			return;
		}

		short sid = annotation.service();
		String[] commands = annotation.command();
		if (commands != null && commands.length != 0) {
			for (String command : commands) {
				String[] parts = command.split("#");

				if (parts != null && parts.length != 0) {
					short cid = Short.parseShort(parts[0]);

					if (parts.length >= 2) {
						int priority = Integer.parseInt(parts[1]);
						mPriorities.put(new Pair<Short, Short>(sid, cid), priority);
					}
					/*
					 * Pair<Short,Short> a = new Pair<Short,Short>(sid, cid);
					 * Pair<Short,Short> b = new Pair<Short,Short>(sid, cid);
					 * System.out.println(a.hashCode());
					 * System.out.println(b.hashCode());
					 */
					mRequests.put(new Pair<Short, Short>(sid, cid), clazz);
					log.info("register succeed " + "sid : " + sid + "cid : " + cid);
				}
			}
		}
		if (handler != null) {
			mHandlers.put(clazz, handler);
		}

	}

	public boolean existsRequest(LinkFrame header) {
		/*
		 * System.out.println(mRequests);
		 * System.out.println(mRequests.containsKey(84));
		 */
		return header == null || mRequests == null ? false
				: mRequests.containsKey(new Pair<Short, Short>(header.serviceId, header.commandId));
	}

	public Integer queryRequestPriority(LinkFrame header) {
		return header == null || mPriorities == null ? null
				: mPriorities.get(new Pair<Short, Short>(header.serviceId, header.commandId));
	}

	public Class<? extends Request> queryRequestClass(LinkFrame header) {
		return header == null || mRequests == null ? null
				: mRequests.get(new Pair<Short, Short>(header.serviceId, header.commandId));
	}

	public RequestHandler queryRequestHandler(Request request) {
		return request == null || mHandlers == null ? null : mHandlers.get(request.getClass());
	}

	public Request newRequest(LinkFrame header) {
		// query class
		Class<? extends Request> clazz = queryRequestClass(header);

		if (clazz == null) {
			return null;
		}

		try {
			// new
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}
}
