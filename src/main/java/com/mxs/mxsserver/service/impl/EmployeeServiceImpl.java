package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mxs.mxsserver.domain.Employee;
import com.mxs.mxsserver.repository.EmployeeRepository;
import com.mxs.mxsserver.service.EmployeeService;
@Transactional
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
	public Employee queryEmployeeByPhone(String phone) {
		return employeeRepository.findByPhoneNumber(phone);
	}

	@Override
	public Employee login4Employee(String tel, String password) {
		return employeeRepository.findByPhoneNumberAndPassword(tel, password);
	}

}
