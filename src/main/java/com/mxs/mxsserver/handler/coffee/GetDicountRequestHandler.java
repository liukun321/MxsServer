package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mxs.mxsserver.domain.DiscountInfo;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.coffee.GetDiscountResponce;
import com.mxs.mxsserver.service.DiscountInfoService;

//查询折扣信息
@Component
public class GetDicountRequestHandler extends RequestHandler {
	@Autowired
	private DiscountInfoService dservice;
	private static DiscountInfoService discountInfoService;
	@PostConstruct
	public void init() {
		discountInfoService = dservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		GetDiscountResponce getDiscountResponce = new GetDiscountResponce(request.getLinkFrame().key);
		getDiscountResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;

//		discount_info result = database.Querydiscount(request.getLinkFrame().key);
		DiscountInfo result = discountInfoService.queryDiscountInfo(request.getLinkFrame().key);
		JSONObject jsonObj = new JSONObject();
		if(null != result) {
			jsonObj.put("discount", result.getDiscount());
			jsonObj.put("reduction", result.getReduction());
		}else {
			jsonObj.put("discount", null);
			jsonObj.put("reduction", null);
		}
		getDiscountResponce.setFavorable(jsonObj.toString());
		ctx.getChannel().write(getDiscountResponce);

	}
}
