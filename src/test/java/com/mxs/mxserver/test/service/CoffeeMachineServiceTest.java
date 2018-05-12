package com.mxs.mxserver.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.service.CoffeeMachineService;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class CoffeeMachineServiceTest {
	@Autowired
	private CoffeeMachineService coffeeMachineService;
	
	@Test
	public void addCoffeeMachineTest() {
		
		CoffeeMachine coffeeMachine = new CoffeeMachine();
		coffeeMachine.setCreateTime(new Date());
		coffeeMachine.setIs_running(true);
		coffeeMachine.setStatus(1);
		coffeeMachine.setMachineId("rr2329unvrnr2rm");
		coffeeMachine.setUpdateTime(new Date());
		coffeeMachine.setNumber(13);
		CoffeeMachine cm = coffeeMachineService.addCoffeeMachine(coffeeMachine);
		System.out.println(cm.toString());
	}
}
