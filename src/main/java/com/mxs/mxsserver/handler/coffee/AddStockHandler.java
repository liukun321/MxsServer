package com.mxs.mxsserver.handler.coffee;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mxs.mxsserver.domain.Material;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.AddStockRequest;
import com.mxs.mxsserver.protocol.responce.coffee.AddStockResponce;
import com.mxs.mxsserver.service.MaterialService;
import com.mxs.mxsserver.service.impl.LoginInfoServiceImpl;

/*
 * 添加物料
 * 
 * AddStockRequest 中的inventory存放者规定编号的料盒中物料的余量，解析时一次更新物料
 */
@Component
public class AddStockHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(AddStockHandler.class);
	@Autowired
	private MaterialService service;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		materialService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		AddStockRequest addStockRequest = (AddStockRequest) request;
		log.info("添加物料--" + addStockRequest.getInventory());
		
		Material m = updateMaterial(addStockRequest);
		AddStockResponce addStockResponce = new AddStockResponce(addStockRequest.getLinkFrame().key);
		addStockResponce.getLinkFrame().serialId = addStockRequest.getLinkFrame().serialId;

		if (null == m)
			addStockResponce.getLinkFrame().resCode = 0;
		ctx.getChannel().write(addStockResponce);

	}
	private Material updateMaterial(AddStockRequest addStockRequest) {
		Material material = new Material();
		material.setMachineId(addStockRequest.getLinkFrame().key);
		String inventory = addStockRequest.getInventory();
		JSONArray array = JSON.parseArray(inventory);
		
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObj = array.getJSONObject(i);
			int id = jsonObj.getInteger("id");

			double value = jsonObj.getDouble("value");
			if (id == 1)
				material.setWater(value);
			else if (id == 2)
				material.setCupnum(value);
			else if (id == 3)
				material.setMilk(value);
			else if (id == 4)
				material.setSugar(value);
			else if (id == 5)
				material.setChocolate(value);
			else if (id == 6)
				material.setMilktea(value);
			else if (id == 7)
				material.setCoffeebean(value);
		}
		Material m = materialService.updateMaterial(material);
		return m;
	}
}
