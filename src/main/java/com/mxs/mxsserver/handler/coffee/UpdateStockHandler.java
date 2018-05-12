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
import com.mxs.mxsserver.protocol.request.coffee.UpdateStockRequest;
import com.mxs.mxsserver.protocol.responce.coffee.UpdateStockResponce;
import com.mxs.mxsserver.server.RequestFactory;
import com.mxs.mxsserver.service.MaterialService;

//咖啡机物料更新
@Component
public class UpdateStockHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(UpdateStockHandler.class);
	@Autowired
	private MaterialService service;
	private static MaterialService materialService;
	@PostConstruct
	public void init() {
		materialService = service;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		UpdateStockRequest updateStockRequest = (UpdateStockRequest) request;
		log.info("更新咖啡机物料=" + updateStockRequest.getLinkFrame().key);
		System.out.println(updateStockRequest.getInventory());

//		int flag = database.UpdateMaterial(updateStockRequest.getLinkFrame().key, updateStockRequest.getInventory());
		Material m = updateMaterial(updateStockRequest);
		UpdateStockResponce updateStockResponce = new UpdateStockResponce(updateStockRequest.getLinkFrame().key);
		updateStockResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
		if (null == m)
			updateStockResponce.getLinkFrame().resCode = 0;
		ctx.getChannel().write(updateStockResponce);
	}
	/**
	 * 更新咖啡机的物料
	 * @param addStockRequest
	 * @return
	 */
	private Material updateMaterial(UpdateStockRequest addStockRequest) {
		Material material = new Material();
		System.out.println(addStockRequest.getLinkFrame().key);
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
