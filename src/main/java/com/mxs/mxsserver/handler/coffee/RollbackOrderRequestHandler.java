package com.mxs.mxsserver.handler.coffee;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.domain.RollbackIndent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.RollbackOrderRequest;
import com.mxs.mxsserver.protocol.responce.coffee.RollbackOrderResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.PayindentService;
import com.mxs.mxsserver.service.RollbackIndentService;
import com.mxs.mxsserver.util.AlipayUtils;
import com.mxs.mxsserver.util.WechatPayUtils;

/**
 * 5 退款
 * @author liukun
 *	如果APP端发现支付成功，但是出货失败，则请求 退款服务
 */
@Component
public class RollbackOrderRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(RollbackOrderRequestHandler.class);
	
	@Autowired
	private RollbackIndentService rservice;
	@Autowired
	private PayindentService pservice;
	private static RollbackIndentService rollbackIndentService;
	private static PayindentService payindentService;
	@PostConstruct
	public void init() {
		rollbackIndentService = rservice;
		payindentService = pservice;
	}
	
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		
		boolean flag = false;
		RollbackOrderRequest rollbackCartRequest = (RollbackOrderRequest) request;
//		JSONArray array = JSON.parseArray(rollbackCartRequest.getCoffeeIndents());
//		JSONObject indent = new JSONObject();
//		int coffeeid;
//		String dosing;
//		double totalprice = 0;
//		for (int i = 0; i < array.size(); i++) {
//			indent = array.getJSONObject(i);
//			coffeeid = indent.getInteger("goodsid");
//			dosing = indent.getString("dosing");
//			totalprice += database.Queryprice(coffeeid);
//			totalprice += coffeeInfoService.queryCoffeeInfoForPrice(coffeeid);
//		}
		String indent_id = rollbackCartRequest.getPayIndent();
		Payindent payindent = payindentService.queryPayindentById(indent_id);
		RollbackIndent rollbackIndent = new RollbackIndent();

		if(null != payindent) {
			rollbackIndent.setIndentId(indent_id);
			rollbackIndent.setPrice(payindent.getPrice());
			rollbackIndent.setReason(rollbackCartRequest.getReason());
			RollbackIndent roll = rollbackIndentService.addRollbackIndent(rollbackIndent);
			String price = Double.toString(payindent.getPrice());
			int pay_method = payindent.getPayMethod();
			if (pay_method == 1) {
				// ali rollback
				try {
					AlipayTradeRefundResponse result = AlipayUtils.alipayTradeRefound(indent_id, payindent.getOrderId(),
							indent_id, price);
					flag = result.isSuccess();
					if (flag) {
						System.out.println("AlipayTradeRefund--退款成功");
					} else {
						System.out.println("AlipayTradeRefund--退款失败");
					} 
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			} else if (pay_method == 2) {
				// weixin rollback
				try {
					Map<String, String> result = WechatPayUtils.refound(indent_id, price, price, roll.getReason());

					String response = result.get("return_code");
					if ("SUCCESS".equals(response) && "SUCCESS".equals(result.get("result_code"))) {
						flag = true;
						log.info("WechatRefund--退款成功");
					} else {
						log.info("WechatRefund--退款失败");
					} 
				} catch (Exception e) {
					log.error(e.getMessage());
				}
		}else {
			log.info("Query Payindent error for " + indent_id);
		}
	}

		log.info(rollbackCartRequest.getPayIndent());
		log.info(rollbackCartRequest.getCoffeeIndents());
		log.info(rollbackCartRequest.getReason());

		RollbackOrderResponce rollbackCartResponce = new RollbackOrderResponce(rollbackCartRequest.getLinkFrame().key);
		rollbackCartResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;

		if (flag)
			rollbackCartResponce.getLinkFrame().resCode = 0;
		ctx.getChannel().write(rollbackCartResponce);//默认是200
	}
	
}
