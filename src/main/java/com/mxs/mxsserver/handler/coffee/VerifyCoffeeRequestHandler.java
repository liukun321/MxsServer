package com.mxs.mxsserver.handler.coffee;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.VerifyCoffeeRequest;
import com.mxs.mxsserver.protocol.responce.coffee.VerifyCoffeeResponce;

//ȡ����ȡ������δ����
public class VerifyCoffeeRequestHandler extends RequestHandler {

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		VerifyCoffeeRequest verifyCoffeeRequest = new VerifyCoffeeRequest();
		System.out.println(verifyCoffeeRequest.getCoffeeIndent());
		System.out.println(verifyCoffeeRequest.getTimestamp());
		VerifyCoffeeResponce verifyCoffeeResponce = new VerifyCoffeeResponce(verifyCoffeeRequest.getLinkFrame().key);
		verifyCoffeeResponce.getLinkFrame().serialId = verifyCoffeeRequest.getLinkFrame().serialId;
		verifyCoffeeResponce.setCoffeeId(1);
		verifyCoffeeResponce.setDosingContent("sss");
		verifyCoffeeResponce.setTimestamp(5);
		ctx.getChannel().write(verifyCoffeeResponce);
	}
}
