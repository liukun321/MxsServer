package com.mxs.mxserver.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Material;
import com.mxs.mxsserver.service.MaterialService;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class MaterialServiceTest {
	@Autowired
	private MaterialService materialService;
	
	@Test
	public void addMaterialTest() {
		Material material = new Material();
		material.setMachineId("ad");
		material.setChocolate(30.00);
		material.setCoffeebean(30.00);
		material.setCupnum(100.00);
		material.setMilk(50.00);
		material.setMilktea(40.00);
		material.setSugar(20.00);
		material.setWater(100.00);
		Material m = materialService.updateMaterial(material);
		System.out.println(m.toString());
	}
}
