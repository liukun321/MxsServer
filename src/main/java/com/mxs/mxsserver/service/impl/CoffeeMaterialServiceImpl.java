package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.CoffeeMaterial;
import com.mxs.mxsserver.repository.CoffeeMaterialRepository;
import com.mxs.mxsserver.repository.MaterialRepository;
import com.mxs.mxsserver.service.CoffeeMaterialService;

@Service
public class CoffeeMaterialServiceImpl implements CoffeeMaterialService{
	
	private CoffeeMaterialRepository coffeeMaterialRepository;
	

	public CoffeeMaterialServiceImpl(CoffeeMaterialRepository coffeeMaterialRepository) {
		super();
		this.coffeeMaterialRepository = coffeeMaterialRepository;
	}


	@Override
	public void addMaterial(CoffeeMaterial material) {
		coffeeMaterialRepository.save(material);
	}

}
