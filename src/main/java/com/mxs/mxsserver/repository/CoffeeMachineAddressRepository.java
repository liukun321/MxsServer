package com.mxs.mxsserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeMachine;
import com.mxs.mxsserver.domain.CoffeeMachineAddress;

@Repository
public interface CoffeeMachineAddressRepository extends JpaRepository<CoffeeMachineAddress, String> {
	
}
