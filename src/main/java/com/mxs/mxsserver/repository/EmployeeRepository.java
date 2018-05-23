package com.mxs.mxsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByPhoneNumber(String phoneNumber);

	Employee findByPhoneNumberAndPassword(String tel, String password);

}
