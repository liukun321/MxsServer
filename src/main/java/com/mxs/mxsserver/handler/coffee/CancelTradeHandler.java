package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.CancelTradeRequest;
import com.mxs.mxsserver.protocol.responce.coffee.CancelTradeResponce;
import com.mxs.mxsserver.service.MaterialService;
import com.mxs.mxsserver.service.PayindentService;

/**
 * 取消交易
 * @author liukun
 * 如果在APP端处理订单时：时将订单详情和用户的条形码信息一起发送到后端，则在APP端可以处理用户出示条形码超时、错误等情况的订单取消
 * 只有在条形码传送成功，但是在后端支付失败，用户选择取消订单，这种情形下才会调用这个请求接口
 */
@Component
public class CancelTradeHandler extends RequestHandler {
	@Autowired
	private PayindentService service;
	private static PayindentService payindentService;
	@PostConstruct
	public void init() {
		payindentService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		CancelTradeRequest cancelTradeCartRequest = (CancelTradeRequest) request;
		boolean flag = payindentService.deletePayindent(cancelTradeCartRequest.getPayIndent());

		CancelTradeResponce cancelTradeCartResponce = new CancelTradeResponce(request.getLinkFrame().key);
		cancelTradeCartResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		//取消订单失败 ？？
		if (!flag)
			cancelTradeCartResponce.getLinkFrame().resCode = 0;
		ctx.getChannel().write(cancelTradeCartResponce);
	}
}
