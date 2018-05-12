package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.marshal.ArrayMable;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class GetDosingListResponce extends Responce {
	public GetDosingListResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	private ArrayMable coffeeDosingList ;
  
	public void setlistlen(int size) {
		coffeeDosingList = new ArrayMable(size);
	}
	
	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}
	
	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putMarshallable(coffeeDosingList);
		return p;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.GET_DOSING;
	}
	
	public ArrayMable getcoffeeDosingList(){
		
		return coffeeDosingList;
	}
	public void setcoffeeDosingList(ArrayMable coffeeDosingList){
		this.coffeeDosingList = coffeeDosingList;
	}
	
}
