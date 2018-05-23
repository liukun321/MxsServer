package com.mxs.mxsserver.handler.coffee;

import org.jboss.netty.channel.ChannelHandlerContext;

import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.AppDownloadRequest;
import com.mxs.mxsserver.protocol.responce.coffee.AppDownloadResponce;

//下载APP
public class AppDownloadRequestHandler extends RequestHandler {

	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		AppDownloadResponce appDownloadResponce = new AppDownloadResponce(request.getLinkFrame().key);
		appDownloadResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		//TODO Url未定
		String Url = "1.1.1.1";
		appDownloadResponce.setAndroidDownloadUrl(Url);
		appDownloadResponce.setIosDownloadUrl(Url);
		ctx.getChannel().write(appDownloadResponce);

	}
}
