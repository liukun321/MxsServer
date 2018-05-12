package com.mxs.mxserver.test.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mxs.mxsserver.MxsServerApplication;
import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.service.EmployeeService;

@RunWith(SpringRunner.class)  
@SpringBootTest(classes = {MxsServerApplication.class}) 
public class EmployeeServiceTest {
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void addEmployeeTest() {
		Employee employee = new Employee();
		employee.setEmployeeCode("243refw43r");
		employee.setEmployeeName("张三");
		employee.setJoinTime(new Date());
		employee.setPassword("123456");
		Set<String> set = new HashSet();
		set.add("rr2329unvrnr2rm");
		set.add("rr2329unvrnr343");
		employee.setVenderNames(set);
		employeeService.insertEmployee(employee);
		System.out.println(employee.toString());
	}
}
