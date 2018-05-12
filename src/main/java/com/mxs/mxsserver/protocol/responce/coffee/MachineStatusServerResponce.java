package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class MachineStatusServerResponce extends Responce {

	public MachineStatusServerResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.MACHINE_STATUS_SERVER;
	}
}
