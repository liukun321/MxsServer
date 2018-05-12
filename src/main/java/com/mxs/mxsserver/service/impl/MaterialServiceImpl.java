package com.mxs.mxsserver.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.Material;
import com.mxs.mxsserver.repository.MaterialRepository;
import com.mxs.mxsserver.service.MaterialService;
@Transactional
@Service
public class MaterialServiceImpl implements MaterialService {
private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);
	
	private MaterialRepository materialRepository;
	
	
	public MaterialServiceImpl(MaterialRepository materialRepository) {
		super();
		this.materialRepository = materialRepository;
	}
	
	@Override
	public Material updateMaterial(Material material) {
		
		return materialRepository.save(material);
	}


	@Override
	public Material queryMaterial(String venderName) {
		// TODO Auto-generated method stub
		return materialRepository.findByMachineId(venderName);
	}

}
