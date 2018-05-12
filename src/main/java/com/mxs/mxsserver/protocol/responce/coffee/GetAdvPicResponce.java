package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class GetAdvPicResponce extends Responce {
	private String advPics;

	public GetAdvPicResponce(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putVarstr(advPics);
	
		return p;
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_ADV_PIC;
	}
	
	public void setAdvPics(String advpics){
		this.advPics = advpics;
	}
	public String getAdvPics(){
		return this.advPics;
	}
	
}
