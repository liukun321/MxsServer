package com.mxs.mxserver.test.service;

import java.util.Date;

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

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class CoffeeInfoServiceTest {
	@Autowired
	private CoffeeInfoService coffeeInfoService;
	
	@Test
	public void addCoffeeMachineTest() {
		
		CoffeeInfo coffeeInfo = new CoffeeInfo();
		coffeeInfo.setCoffeeId("101");
		coffeeInfo.setCoffeeName("美式");
		coffeeInfo.setImgurl("http://mixiusi.com.cn/prod_introduce/coffee/卡其经典拿铁.png");
		coffeeInfo.setIs_hot(true);
		coffeeInfo.setIs_new(true);
		coffeeInfo.setIsSugar(false);
		coffeeInfo.setPrice(16.00);
		coffeeInfo.setDiscount(false);
		coffeeInfo.setDiscount_price(16.00);
		CoffeeInfo cm = coffeeInfoService.addCoffeeInfo(coffeeInfo);
		System.out.println(cm.toString());
	}
}
