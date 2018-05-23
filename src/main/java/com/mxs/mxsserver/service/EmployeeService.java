package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.Employee;

public interface EmployeeService {
	void insertEmployee(Employee employee);
	
	Employee queryEmployeeByPhone(String phone);
	
	Employee login4Employee(String tel, String password);
	
		
}
