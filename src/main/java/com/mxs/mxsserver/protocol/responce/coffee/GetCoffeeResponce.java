package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.marshal.ArrayMable;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class GetCoffeeResponce extends Responce {
	private ArrayMable coffeeInfos;
	private String favorable;
	
	public GetCoffeeResponce(String uid) {
		super(uid);
	}
	
	public void setarraylen(int size){
		coffeeInfos = new ArrayMable(size);
	}
	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putMarshallable(coffeeInfos);
		p.putVarstr(favorable);
		return p;
	}
	
	public void setCoffeeInfos(ArrayMable coffeeInfos){
		this.coffeeInfos = coffeeInfos;
	}
	
	public ArrayMable getCoffeeInfos(){
		return this.coffeeInfos;
	}
	
	public void setFavorable(String favorable) {
		this.favorable = favorable;
	}
	
	public String getFavorable() {
		return favorable;
	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_COFFEE;
	}
}
