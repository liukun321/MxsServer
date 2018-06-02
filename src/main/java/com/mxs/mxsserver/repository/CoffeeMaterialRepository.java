package com.mxs.mxsserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mxs.mxsserver.domain.CoffeeMaterial;


@Repository
public interface CoffeeMaterialRepository extends JpaRepository<CoffeeMaterial, Integer>, JpaSpecificationExecutor<CoffeeMaterial>{

	List<CoffeeMaterial> findByMachineId(String machineId);

	CoffeeMaterial findByMachineIdAndStackNumber(String machineId, Integer number);

	List<CoffeeMaterial> findByMachineIdAndStatus(String machineId, Integer status);

}
