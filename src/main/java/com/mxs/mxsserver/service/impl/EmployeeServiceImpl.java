package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.repository.EmployeeRepository;
import com.mxs.mxsserver.service.EmployeeService;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public void insertEmployee(Employee employee) {
		Employee empl = employeeRepository.save(employee);
		System.out.println(empl);

	}

	@Override
	public Employee queryEmployeeById(String employeeCode) {
		return employeeRepository.findByEmployeeCode(employeeCode);
	}

}
