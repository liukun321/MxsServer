package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class VerifyCoffeeResponce extends Responce {
	private int coffeeId;
	private String dosingContent;
	private long timestamp;
	
	public VerifyCoffeeResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putInt(coffeeId);
        pack.putVarstr(dosingContent);
        pack.putLong(timestamp);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.VERIFY_CODE;
	}

	public int getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(int coffeeId) {
		this.coffeeId = coffeeId;
	}
	public String getDosingContent() {
		return dosingContent;
	}
	public void setDosingContent(String dosingContent) {
		this.dosingContent = dosingContent;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
