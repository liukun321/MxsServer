package com.mxs.mxsserver.service.impl;

import org.springframework.stereotype.Service;

import com.mxs.mxsserver.domain.CoffeeMachineAddress;
import com.mxs.mxsserver.repository.CoffeeMachineAddressRepository;
import com.mxs.mxsserver.service.MachineAddressService;
@Service
public class MachineAddressServiceImpl implements MachineAddressService {
	
	private CoffeeMachineAddressRepository machineAddressRepository;
	
	
	public MachineAddressServiceImpl(CoffeeMachineAddressRepository machineAddressRepository) {
		super();
		this.machineAddressRepository = machineAddressRepository;
	}


	@Override
	public CoffeeMachineAddress updateMachineAddress(CoffeeMachineAddress machineAddress) {
		return machineAddressRepository.save(machineAddress);
	}


	@Override
	public CoffeeMachineAddress queryMachineAddressById(String machineId) {
		return machineAddressRepository.findOne(machineId);
	}

}
