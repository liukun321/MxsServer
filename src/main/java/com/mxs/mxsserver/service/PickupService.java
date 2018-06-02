package com.mxs.mxsserver.service;

import com.mxs.mxsserver.domain.Pickup;

public interface PickupService {
	
	Pickup queryPickup(String pkCode);
}
