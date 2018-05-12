package com.mxs.mxsserver.handler;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.server.*;

//��������
public abstract class RequestHandler {
//	protected Database database = Database.sharedInstance();

	abstract public void processRequest(Request request, ChannelHandlerContext ctx);

}
