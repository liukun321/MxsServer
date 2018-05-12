package com.mxs.mxsserver.handler;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.KeepAliveResponce;

//保持连接
public class KeepAliveRequestHandle extends RequestHandler {

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		KeepAliveResponce keepAliveResponce = new KeepAliveResponce(request.getLinkFrame().key);
		ctx.getChannel().write(keepAliveResponce);
	}

}
