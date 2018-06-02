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
		Map<String, Integer> map = JSON.parseObject(inventory, Map.class);
			/*1号咖啡豆盒：正常咖啡豆（缺料预警）
				2号咖啡豆盒：低因咖啡豆（缺料预警）
				3号粉料盒：抹茶粉（不作监测）
				4号粉料盒：可可粉（不作监测）
				5号液体盒：牛奶（缺料预警）
				6号液体盒：香草糖浆（不作监测）
				7号液体盒：榛果糖浆（不作监测）
				8号液体盒：焦糖糖浆（不作监测）
				9号液体盒：纯糖浆（不作监测）
				10号水盒：桶装水（缺料预警）
				11号：杯子数量*/
		//{"cupNum":"6","number1":"1","number2":"3","number3":"6","number4":"9","number9":"9","water":"9"}
		for(Entry<String, Integer> entry: map.entrySet()){
			String key = entry.getKey();
			Integer value = entry.getValue();
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
