package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.Employee;

public interface EmployeeService {
	void insertEmployee(Employee employee);
	
	Employee queryEmployeeById(String employeeCode);
	
	
	
		
}
