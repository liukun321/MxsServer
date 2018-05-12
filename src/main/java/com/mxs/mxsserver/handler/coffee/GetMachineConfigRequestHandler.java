package com.mxs.mxsserver.handler.coffee;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.GetMachineConfigRequest;
import com.mxs.mxsserver.protocol.responce.coffee.GetMachineConfigResponce;

//��ȡ����������Ϣ
public class GetMachineConfigRequestHandler extends RequestHandler {

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {

		String workTemp;
		String keepTemp;
		String washTime;
		GetMachineConfigResponce getMachineConfigResponce = new GetMachineConfigResponce(request.getLinkFrame().key);
		getMachineConfigResponce.setKeepTemp("50");
		getMachineConfigResponce.setWashTime("50");
		getMachineConfigResponce.setWorkTemp("50");
		getMachineConfigResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(getMachineConfigResponce);

	}
}
