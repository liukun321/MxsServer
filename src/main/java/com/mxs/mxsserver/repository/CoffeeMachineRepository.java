package com.mxs.mxsserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeMachine;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, String> {

	List<CoffeeMachine> findByMachineId(String venderName);

	List<CoffeeMachine> findByEmployeeCode(String employeeCode);

}
