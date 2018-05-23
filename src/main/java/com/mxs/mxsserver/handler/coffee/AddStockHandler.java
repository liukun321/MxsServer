package com.mxs.mxsserver.handler.coffee;

import java.util.Map;
import java.util.Map.Entry;

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
		Map<String, String> map = JSON.parseObject(inventory, Map.class);
		
		for(Entry<String, String> entry: map.entrySet()){
			String key = entry.getKey();
			double value = Double.parseDouble(entry.getValue());
			if ("number1".equals(key))
				material.setCoffeebean(value);
			if ("number2".equals(key))
				material.setLcoffeebean(value);
			if ("number3".equals(key))
				material.setMaccha_powder(value);
			if ("number4".equals(key))
				material.setCocoa_powder(value);
			if ("number5".equals(key))
				material.setMilk(value);
			if ("number6".equals(key))
				material.setVanilla_sugar(value);
			if ("number7".equals(key))
				material.setVanilla_sugar(value);
			if ("number8".equals(key))
				material.setCaramel_sugar(value);
			if ("number9".equals(key))
				material.setPure_sugar(value);
			if ("number10".equals(key))
				material.setWater(value);
			if ("number11".equals(key))
				material.setCupnum(value);
		}
		Material m = materialService.updateMaterial(material);
		return m;
	}
}
