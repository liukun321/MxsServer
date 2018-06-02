package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.Pickup;
import com.mxs.mxsserver.repository.PickupRepository;
import com.mxs.mxsserver.service.PickupService;
@Service
public class PickupServiceImpl implements PickupService {
	
	private PickupRepository pickupRepository;
	
	public PickupServiceImpl(PickupRepository pickupRepository) {
		super();
		this.pickupRepository = pickupRepository;
	}

	@Override
	public Pickup queryPickup(String pkCode) {
		return pickupRepository.findByPickupCode(pkCode);
	}


}
