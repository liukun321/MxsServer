package com.mxs.mxsserver.handler.coffee;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PayStatusAskCartRequest;
import com.mxs.mxsserver.protocol.responce.coffee.PayStatusAskCartResponce;
import com.mxs.mxsserver.service.PayindentService;
import com.mxs.mxsserver.util.AlipayUtils;
import com.mxs.mxsserver.util.WechatPayUtils;

/**
 * 4 支付状态请求
 * @author liukun
 *	返回支付状态的枚举值，APP端进行判断实际的状态
 */
@Component
public class PayStatusCartRequestHandler extends RequestHandler {
	@Autowired
	private PayindentService service;
	private static PayindentService payindentService;
	@PostConstruct
	public void init() {
		payindentService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		PayStatusAskCartRequest payStatusAskCartRequest = (PayStatusAskCartRequest) request;
		System.out.println(payStatusAskCartRequest.getPayIndent());
		String payId = payStatusAskCartRequest.getPayIndent();
		Payindent payindent = payindentService.queryPayindentById(payId);
		String orderId = payindent.getOrderId();
		boolean tradeStatus = false;
		
		PayStatusAskCartResponce payStatusAskCartResponce = new PayStatusAskCartResponce(
				payStatusAskCartRequest.getLinkFrame().key);
		if(null != payindent && 0 == payindent.getPayMethod()) {//订单是待支付状态
			int payMethod = payindent.getPayMethod();//1 支付宝 2微信
			switch (payMethod) {
			case 1:
				//调用支付宝订单查询方法
				try {
					AlipayTradeQueryResponse result = AlipayUtils.alipayTradeQuery(payId, orderId);
					String code = result.getCode();
					if("10000".equals(code)) {//请求成功
						String statuxs = result.getTradeStatus();
						if("TRADE_SUCCESS".equals(statuxs)) {//支付成功
							System.out.println("支付成功");
							tradeStatus = true;
						}
					}
				} catch (AlipayApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 2:
				//调用微信订单查询方法
				try {
					Map<String, String> result = WechatPayUtils.queryOrder(payId);
					String return_code = result.get("return_code");
					String result_code = result.get("result_code");
					if("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
						tradeStatus = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			
		}
		
		if (tradeStatus) {//订单存在且支付状态是成功
			payStatusAskCartResponce.setPayStatus(1);
			payStatusAskCartResponce.getLinkFrame().resCode = 200;
		}else {
			payStatusAskCartResponce.setPayStatus(2);
			payStatusAskCartResponce.getLinkFrame().resCode = 501;
		}

		payStatusAskCartResponce.getLinkFrame().serialId = payStatusAskCartRequest.getLinkFrame().serialId;
		ctx.getChannel().write(payStatusAskCartResponce);
	}
}
