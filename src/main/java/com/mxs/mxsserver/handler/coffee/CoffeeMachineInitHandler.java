package com.mxs.mxsserver.handler.coffee;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.CoffeeMaterial;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.coffee.AddStockRequest;
import com.mxs.mxsserver.protocol.responce.coffee.AddStockResponce;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.service.CoffeeMaterialService;
import com.mxs.mxsserver.util.MaterialEnum;

/**
 * 咖啡机初始化信息
 * 初始化料盒信息 默认是正常状态
 * 初始化咖啡机自身的信息
 * @author liukun
 *
 */
@Component
public class CoffeeMachineInitHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(CoffeeMachineInitHandler.class);
	@Autowired
	private CoffeeMaterialService service;
	private static CoffeeMaterialService coffeeMaterialService;
	@Autowired
	private CoffeeMachineService cservice;
	private static CoffeeMachineService coffeeMachineService;
	@PostConstruct
	public void init() {
		coffeeMaterialService = service;
		coffeeMachineService = cservice;
	}
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		AddStockRequest addStockRequest = (AddStockRequest) request;
		log.info("添加物料--" + addStockRequest.getInventory());
		List<CoffeeMaterial> list = new ArrayList();
		String machineId = addStockRequest.getLinkFrame().key;
		for(int i = 0; i< 11; i++) {
			CoffeeMaterial cmaterial = new CoffeeMaterial();
			cmaterial.setCategory(MaterialEnum.getName(i));
			cmaterial.setMachineId(machineId);
			cmaterial.setStackNumber(i);
			cmaterial.setStatus(0);
			list.add(cmaterial);
		}
		//TODO 批量保存数据
		coffeeMaterialService.batchInsertMaterial(list);
		
		//初始化咖啡机信息
		CoffeeMachine cm = new CoffeeMachine();
		
		
		coffeeMachineService.addCoffeeMachine(cm);
//		Material m = updateMaterial(addStockRequest);

//		if (null == m)
//			addStockResponce.getLinkFrame().resCode = 0;
		AddStockResponce addStockResponce = new AddStockResponce(addStockRequest.getLinkFrame().key);
		addStockResponce.getLinkFrame().serialId = addStockRequest.getLinkFrame().serialId;
		ctx.getChannel().write(addStockResponce);

	}
//	private Material updateMaterial(AddStockRequest addStockRequest) {
//		Material material = new Material();
//		material.setMachineId(addStockRequest.getLinkFrame().key);
//		String inventory = addStockRequest.getInventory();
//		Map<String, String> map = JSON.parseObject(inventory, Map.class);
//		
//		for(Entry<String, String> entry: map.entrySet()){
//			String key = entry.getKey();
//			double value = Double.parseDouble(entry.getValue());
//			if ("number1".equals(key))
//				material.setCoffeebean(value);
//			if ("number2".equals(key))
//				material.setLcoffeebean(value);
//			if ("number3".equals(key))
//				material.setMaccha_powder(value);
//			if ("number4".equals(key))
//				material.setCocoa_powder(value);
//			if ("number5".equals(key))
//				material.setMilk(value);
//			if ("number6".equals(key))
//				material.setVanilla_sugar(value);
//			if ("number7".equals(key))
//				material.setVanilla_sugar(value);
//			if ("number8".equals(key))
//				material.setCaramel_sugar(value);
//			if ("number9".equals(key))
//				material.setPure_sugar(value);
//			if ("number10".equals(key))
//				material.setWater(value);
//			if ("number11".equals(key))
//				material.setCupnum(value);
//		}
////		Material m = materialService.updateMaterial(material);
//		
//		
//		
//		
//		
//		return null;
//	}
}
