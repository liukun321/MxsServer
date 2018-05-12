package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class PayNotifyResponce extends Responce{
	private String coffeeindent;
	
	public PayNotifyResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putVarstr(coffeeindent);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_RESULT;
	}
	
	public String getCoffeeindent() {
		return coffeeindent;
	}
	
	public void setCoffeeindent(String coffeeindent) {
		this.coffeeindent = coffeeindent;
	}
}


