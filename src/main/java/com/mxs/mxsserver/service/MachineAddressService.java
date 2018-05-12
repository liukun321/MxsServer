package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.CoffeeMachineAddress;

public interface MachineAddressService {
	//保存和更新咖啡机地理位置
	public CoffeeMachineAddress updateMachineAddress(CoffeeMachineAddress machineAddress);
	
	public CoffeeMachineAddress queryMachineAddressById(String machineId);
	
	
}
