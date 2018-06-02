package com.mxs.mxserver.test.repository;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Coupons;
import com.mxs.mxsserver.repository.CouponsRepository;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class CouponRepositoryTest {
	@Autowired
	private CouponsRepository couponsRepository;
	
	@Test
	public void saveTest() {
//		Coupons con = new Coupons();
//		con.setCouponCode("654321");
//		Calendar cal = Calendar.getInstance();
//		Date date = new Date();
////		cal.setTime(date);
////		cal.add(Calendar.DAY_OF_MONTH, +5);
//		con.setEndTime(date);
//		con.setValue("3.00");
//		con.setId(10002);
//		Coupons cc = couponsRepository.save(con);
////		Coupons cc = couponsRepository.findByCouponCodeAndEndTimeAfter("123456", date);
//		String dic = cc.getDiscount();
//		if(null != cc)
//			System.out.println(cc.toString());
//		System.out.println("没有找到优惠券");
	}
}
