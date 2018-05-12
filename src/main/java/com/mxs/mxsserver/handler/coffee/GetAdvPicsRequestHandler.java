package com.mxs.mxsserver.handler.coffee;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.coffee.GetAdvPicResponce;

/**
 * 1 获取咖机首页图片
 * @author liukun
 *	咖啡机上的第一步，获取首页图片
 */
public class GetAdvPicsRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetAdvPicsRequestHandler.class);
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("1111---获取首页图片");
		// TODO Auto-generated method stub
		// String advurl = " ";
		JSONArray advurls = new JSONArray();
		advurls.add(
				"http://m.qpic.cn/psb?/V11PUUCS0IrXcc/wp5mGNmL*Z*7obxrAEUcR3PUPzjHKu4*O4IDo4gWXMQ!/b/dGcBAAAAAAAA&bo=9AFTAQAAAAARF4c!&rf=viewer_4");
		String advurl = advurls.toString();
		GetAdvPicResponce getAdvPicResponce = new GetAdvPicResponce(request.getLinkFrame().key);
		getAdvPicResponce.setAdvPics(advurl);
		getAdvPicResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		ctx.getChannel().write(getAdvPicResponce);

	}
}
