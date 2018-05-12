package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class RollbackResponce extends Responce {
	private long rbTimestamp;
	private long qhTimestamp;
	
	public RollbackResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putLong(rbTimestamp);
        pack.putLong(qhTimestamp);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.ROLL_BACK;
	}
	
	public void setQhTimestamp(long qhTimestamp) {
		this.qhTimestamp = qhTimestamp;
	}
	public long getQhTimestamp() {
		return qhTimestamp;
	}
	public void setRbTimestamp(long rbTimestamp) {
		this.rbTimestamp = rbTimestamp;
	}
	public long getRbTimestamp() {
		return rbTimestamp;
	}


}
