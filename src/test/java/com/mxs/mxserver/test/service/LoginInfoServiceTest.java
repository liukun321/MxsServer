package com.mxs.mxserver.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.LoginInfo;
import com.mxs.mxsserver.repository.LoginInfoRepository;
import com.mxs.mxsserver.service.LoginInfoService;
import com.mxs.mxsserver.util.Enums.Login;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class LoginInfoServiceTest {
	@Autowired
	private LoginInfoService loginInfoService;
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Test
	public void loginTest() {
		
		Login ll = loginInfoService.queryUserInfo("1", "2");
		System.out.println(ll);
		
		LoginInfo loginInfo = loginInfoRepository.findByVenderName("ad");
		if(null != loginInfo) {
			
			System.out.println(loginInfo.getVenderPassword());
		}
		
	}
}
