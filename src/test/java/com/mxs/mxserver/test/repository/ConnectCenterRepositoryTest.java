package com.mxs.mxserver.test.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.ConnectCenter;
import com.mxs.mxsserver.repository.ConnectCenterRepository;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class ConnectCenterRepositoryTest {
	@Autowired
	private ConnectCenterRepository connectCenterRepository;
	
	@Test
	public void saveTest() {
//		ConnectCenter cc = new ConnectCenter();
//		cc.setPhone("15855547087");
//		cc.setTeam("机械团队");
//		cc.setUrl("http://baidu.com");
//		cc.setId(2002);
//		connectCenterRepository.save(cc);
		
	}
}
