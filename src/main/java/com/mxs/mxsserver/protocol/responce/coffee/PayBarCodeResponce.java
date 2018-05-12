package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class PayBarCodeResponce extends Responce {
	//订单ID
	private String payIndent;
	//咖啡商品Id
	private String coffeeId;
	//价格
	private String price;
	
	private String status;
	
//	private String favorable;

	public PayBarCodeResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putVarstr(payIndent);
        pack.putVarstr(coffeeId);
        pack.putVarstr(price);
        pack.putVarstr(status);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_BAR_CODE;
	}


	public String getPayIndent() {
		return payIndent;
	}


	public String getCoffeeId() {
		return coffeeId;
	}


	public String getPrice() {
		return price;
	}


	public String getStatus() {
		return status;
	}


	public void setPayIndent(String payIndent) {
		this.payIndent = payIndent;
	}


	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	
}
