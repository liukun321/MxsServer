package com.mxs.mxsserver.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.CoffeeMaterial;
import com.mxs.mxsserver.repository.CoffeeMaterialRepository;
import com.mxs.mxsserver.repository.MaterialRepository;
import com.mxs.mxsserver.service.CoffeeMaterialService;

@Service
public class CoffeeMaterialServiceImpl implements CoffeeMaterialService{
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;
	private CoffeeMaterialRepository coffeeMaterialRepository;
	

	public CoffeeMaterialServiceImpl(CoffeeMaterialRepository coffeeMaterialRepository) {
		super();
		this.coffeeMaterialRepository = coffeeMaterialRepository;
	}


	@Override
	public void addMaterial(CoffeeMaterial material) {
		coffeeMaterialRepository.save(material);
	}


	@Override
	public List<CoffeeMaterial> queryMaterialByMachineId(String machineId) {
		return coffeeMaterialRepository.findByMachineId(machineId);
	}


	@Override
	public CoffeeMaterial queryMaterialByIdAndNumber(String machineId, Integer number) {
		return coffeeMaterialRepository.findByMachineIdAndStackNumber(machineId, number);
	}


	@Override
	public void batchInsertMaterial(List<CoffeeMaterial> list) {
		//这里list只有11条数据，所以省略一次性插入条数的判断
		list.stream().forEach(cm -> {
			entityManager.persist(cm);
		});
		entityManager.flush();
		entityManager.clear();
	}


	@Override
	public List<CoffeeMaterial> queryMaterialByStatus(String machineId, Integer status) {
		return coffeeMaterialRepository.findByMachineIdAndStatus(machineId, status);
	}

}
