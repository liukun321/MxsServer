package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.PayStatusAskCartRequest;
import com.mxs.mxsserver.protocol.responce.coffee.PayStatusAskCartResponce;
import com.mxs.mxsserver.service.PayindentService;

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

//		int statu = database.Querystatus(payStatusAskCartRequest.getPayIndent());
		Payindent p = payindentService.queryPayindentById(payStatusAskCartRequest.getPayIndent());
		PayStatusAskCartResponce payStatusAskCartResponce = new PayStatusAskCartResponce(
				payStatusAskCartRequest.getLinkFrame().key);

		if (null != p) {//订单存在且支付状态是成功
			payStatusAskCartResponce.setPayStatus(p.getPayStatus());
			payStatusAskCartResponce.getLinkFrame().resCode = 200;
		}else {
			payStatusAskCartResponce.getLinkFrame().resCode = 501;
		}

		payStatusAskCartResponce.getLinkFrame().serialId = payStatusAskCartRequest.getLinkFrame().serialId;
		ctx.getChannel().write(payStatusAskCartResponce);
	}
}
