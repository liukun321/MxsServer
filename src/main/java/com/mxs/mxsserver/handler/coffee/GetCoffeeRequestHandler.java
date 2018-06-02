package com.mxs.mxsserver.handler.coffee;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mxs.mxsserver.bean.Coffee;
import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.domain.DiscountInfo;
import com.mxs.mxsserver.handler.RequestHandler;
import com.mxs.mxsserver.protocol.marshal.Property;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.responce.coffee.GetCoffeeResponce;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.DiscountInfoService;

/**
 * 2 获取所有品种的咖啡信息
 * @author liukun
 *点击咖啡机的首页后进入咖啡产品的列表页面
 */
@Component
public class GetCoffeeRequestHandler extends RequestHandler {
	private final Logger log = LoggerFactory.getLogger(GetCoffeeRequestHandler.class);
	@Autowired
	private CoffeeInfoService service;
	@Autowired
	private DiscountInfoService dservice;
	private static CoffeeInfoService coffeeInfoService;
	private static DiscountInfoService discountInfoService;
	@PostConstruct
	public void init() {
		coffeeInfoService = service;
		discountInfoService = dservice;
	}
	
	@Override
	public void processRequest(Request request, ChannelHandlerContext ctx) {
		log.info("----------查询咖啡的所有产信息--------------------");
		GetCoffeeResponce getCoffeeResponce = new GetCoffeeResponce(request.getLinkFrame().key);
		List<CoffeeInfo> coffeeInfoList = coffeeInfoService.queryAllCoffeeInfo();
//		List<Coffee> coffeelists = new ArrayList();
		if(null != coffeeInfoList && !coffeeInfoList.isEmpty()) {
			getCoffeeResponce.setarraylen(coffeeInfoList.size());
//			for (CoffeeInfo coffeeInfo : coffeeInfoList) {
//				Coffee cc = new Coffee();
//				coffeelists.add(cc);
//				BeanUtils.copyProperties(coffeeInfo, cc);
//			}
			log.info(coffeeInfoList.toString());
			for (int i = 0; i < coffeeInfoList.size(); i++) {
				Property property = new Property();
				
				property.put(1, coffeeInfoList.get(i).getCoffeeId());
				property.put(2, coffeeInfoList.get(i).getCoffeeName());
				property.put(3, Double.toString(coffeeInfoList.get(i).getPrice()));
				property.put(4, coffeeInfoList.get(i).getImgurl());
				log.info("图片地址：" + coffeeInfoList.get(i).getImgurl());
				//咖啡的糖度 信息
				property.put(5, Boolean.toString(coffeeInfoList.get(i).getIsSugar()));
				property.put(6, Boolean.toString(coffeeInfoList.get(i).getIs_new()));
				property.put(7, Boolean.toString(coffeeInfoList.get(i).getIs_hot()));
				property.put(8, Boolean.toString(coffeeInfoList.get(i).getDiscount()));
				property.put(9, Double.toString(coffeeInfoList.get(i).getDiscount_price()));
				getCoffeeResponce.getCoffeeInfos().list.add(property);
				
			}
			//获取咖啡机的折扣信息
			DiscountInfo result = discountInfoService.queryDiscountInfo(request.getLinkFrame().key);
			JSONObject jsonObj = new JSONObject();
			if(null != result) {
				jsonObj.put("discount", result.getDiscount());
			}else {
				jsonObj.put("discount", null);
			}
			getCoffeeResponce.setFavorable(jsonObj.toString());
			
			System.out.println("getcoffeeinfo");
			getCoffeeResponce.getLinkFrame().serialId = request.getLinkFrame().serialId;
			log.info(getCoffeeResponce.getCoffeeInfos().list.get(0).toString());
		}
		ctx.getChannel().write(getCoffeeResponce);
	}

}
