package com.mxs.mxserver.test.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Payindent;
import com.mxs.mxsserver.service.PayindentService;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class PayIndentServiceTest {
	@Autowired
	private PayindentService payindentService;
	
	@Test
	public void addPayindentTest() {
		Payindent payindent = new Payindent();
		payindent.setCoffeeId("2339fr");
		payindent.setCreateTime(new Date());
		payindent.setIndentId("20180505154823");
		payindent.setOrderId("243fdr32get");
		payindent.setPayMethod(1);
		payindent.setPayStatus(2);
		payindent.setPrice(23.45);
		payindent.setPriceOri(25.00);
		payindent.setMachineId("35eerjh3jwe333");
		payindent.setSugar(1);
		payindent.setHot(false);
		Payindent pi = payindentService.addPayindent(payindent);
		System.out.println(pi.toString());
	}
}
