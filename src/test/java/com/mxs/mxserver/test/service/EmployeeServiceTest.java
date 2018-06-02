package com.mxs.mxserver.test.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.service.EmployeeService;
import com.mxs.mxsserver.util.MD5;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class EmployeeServiceTest {
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void addEmployeeTest() {
//		Employee employee = new Employee();
//		employee.setWorkerId("454rt3ew2");
//		employee.setNickname("bingjun");
//		employee.setRealname("bingjun");
//		employee.setJoinTime(new Date());
//		employee.setPassword(MD5.md5("123456"));
//		employee.setPhoneNumber("17682440224");
//		employee.setPhoto("http://mixiusi.com.cn/prod_introduce/coffee/醇香牛奶.png");
//		Map<String, Integer> map = new HashMap();
//		map.put("55334242", 0);
//		map.put("12434288", 1);
//		employee.setMachines(map);
//		employeeService.insertEmployee(employee);
//		System.out.println(employee.toString());
	}
}
