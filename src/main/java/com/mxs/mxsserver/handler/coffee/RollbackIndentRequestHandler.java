package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.domain.RollbackIndent;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.RollbackRequest;
import com.mxs.mxsserver.protocol.responce.coffee.RollbackResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.RollbackIndentService;

@Component
public class RollbackIndentRequestHandler extends RequestHandler {

	@Autowired
	private CoffeeInfoService service;
	private static CoffeeInfoService coffeeInfoService;
	@Autowired
	private RollbackIndentService rservice;
	private static RollbackIndentService rollbackIndentService;
	@PostConstruct
	public void init() {
		rollbackIndentService = rservice;
		coffeeInfoService = service;
	}
	
	@Override
	public void processRequest(Request request,ChannelHandlerContext ctx) {
		RollbackRequest rollbackRequest = new RollbackRequest();
		System.out.println(rollbackRequest.getCoffeeIndent());
		System.out.println(rollbackRequest.getTimestamp());
		
		JSONObject indent = new JSONObject();
		indent = JSONObject.parseObject(rollbackRequest.getCoffeeIndent());
		
		int coffeeid = indent.getInteger("goodsid");
		
//		double price =  database.Queryprice(coffeeid);
		double price = coffeeInfoService.queryCoffeeInfoForPrice(coffeeid).getDiscount_price();
		
		String indentid = rollbackRequest.getLinkFrame().key + Long.toString(rollbackRequest.getTimestamp());
		
//		int flag = database.insertroll(indentid, price, "");
		RollbackIndent rollbackIndent = new RollbackIndent();
		rollbackIndent.setIndentId(indentid);
		rollbackIndent.setPrice(price);
		rollbackIndent.setReason("");
		RollbackIndent ri = rollbackIndentService.addRollbackIndent(rollbackIndent);
		
		RollbackResponce rollbackResponce = new RollbackResponce(rollbackRequest.getLinkFrame().key);
		rollbackResponce.getLinkFrame().serialId = rollbackRequest.getLinkFrame().serialId;
		rollbackResponce.setQhTimestamp(rollbackRequest.getTimestamp());
		rollbackResponce.setRbTimestamp(rollbackRequest.getTimestamp());
		ctx.getChannel().write(rollbackResponce);
	}
}
