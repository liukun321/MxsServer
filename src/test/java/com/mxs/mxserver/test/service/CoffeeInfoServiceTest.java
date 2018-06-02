package com.mxs.mxserver.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.CoffeeInfo;
import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.service.CoffeeInfoService;
import com.mxs.mxsserver.service.CoffeeMachineService;
import com.mxs.mxsserver.util.RandomStringGenerator;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class CoffeeInfoServiceTest {
	@Autowired
	private CoffeeInfoService coffeeInfoService;
	
	@Test
	public void addCoffeeMachineTest() {
		
//		CoffeeInfo coffeeInfo1 = new CoffeeInfo();
//		coffeeInfo1.setCoffeeId(RandomStringGenerator.getRandomInteger(8).toString());
//		coffeeInfo1.setCoffeeName("冰牛奶");
//		coffeeInfo1.setImgurl("http://mixiusi.com.cn/prod_introduce/coffee/卡其经典拿铁.png");
//		coffeeInfo1.setIs_hot(true);
//		coffeeInfo1.setIs_new(true);
//		coffeeInfo1.setIsSugar(false);
//		coffeeInfo1.setPrice(16.00);
//		coffeeInfo1.setDiscount(false);
//		coffeeInfo1.setDiscount_price(16.00);
//		coffeeInfoService.addCoffeeInfo(coffeeInfo1);
//		CoffeeInfo coffeeInfo2 = new CoffeeInfo();
//		coffeeInfo2.setCoffeeId(RandomStringGenerator.getRandomInteger(8).toString());
//		coffeeInfo2.setCoffeeName("冰可可");
//		coffeeInfo2.setImgurl("http://mixiusi.com.cn/prod_introduce/coffee/卡其经典拿铁.png");
//		coffeeInfo2.setIs_hot(true);
//		coffeeInfo2.setIs_new(true);
//		coffeeInfo2.setIsSugar(false);
//		coffeeInfo2.setPrice(16.00);
//		coffeeInfo2.setDiscount(false);
//		coffeeInfo2.setDiscount_price(16.00);
//		coffeeInfoService.addCoffeeInfo(coffeeInfo2);
//		CoffeeInfo coffeeInfo3 = new CoffeeInfo();
//		coffeeInfo3.setCoffeeId(RandomStringGenerator.getRandomInteger(8).toString());
//		coffeeInfo3.setCoffeeName("冰抹茶拿铁");
//		coffeeInfo3.setImgurl("http://mixiusi.com.cn/prod_introduce/coffee/卡其经典拿铁.png");
//		coffeeInfo3.setIs_hot(true);
//		coffeeInfo3.setIs_new(true);
//		coffeeInfo3.setIsSugar(false);
//		coffeeInfo3.setPrice(16.00);
//		coffeeInfo3.setDiscount(false);
//		coffeeInfo3.setDiscount_price(16.00);
//		CoffeeInfo cm = coffeeInfoService.addCoffeeInfo(coffeeInfo3);
//		System.out.println(cm.toString());
	}
}
