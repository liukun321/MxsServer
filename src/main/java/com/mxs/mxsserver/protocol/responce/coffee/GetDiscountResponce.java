package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class GetDiscountResponce extends Responce {
	private String favorable;

	public GetDiscountResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putVarstr(favorable);
	
		return p;
	}
	
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_DISCOUNT;
	}
	
	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}
	public String getFavorable() {
		return favorable;
	}
}
