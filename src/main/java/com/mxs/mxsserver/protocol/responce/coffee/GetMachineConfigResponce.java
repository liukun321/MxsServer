package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class GetMachineConfigResponce extends Responce {
	private String workTemp;
	private String keepTemp;
	private String washTime;

	public GetMachineConfigResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putVarstr(workTemp);
		p.putVarstr(keepTemp);
		p.putVarstr(washTime);
		return p;
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_MACHINE_CONFIG;
	}
	public void setWorkTemp(String worktemp){
		this.workTemp = worktemp;
	}
	public String getWorkTemp(){
		return this.workTemp;
	}
	public void setKeepTemp(String keepTemp){
		this.keepTemp = keepTemp;
	}
	public String getKeepTemp(){
		return this.keepTemp;
	}
	public void setWashTime(String washTime){
		this.washTime = washTime;
	}
	public String getWashTime(){
		return this.washTime;
	}
}
