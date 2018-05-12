package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class PayQrcodeResponce extends Responce {
	private String coffeeIndent;
	private String qrcodeUrl;
	private String price;
	
	public PayQrcodeResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putVarstr(coffeeIndent);
        pack.putVarstr(qrcodeUrl);
        pack.putVarstr(price);
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_QRCODE;
	}
	public String getCoffeeIndent() {
		return coffeeIndent;
	}
	public void setCoffeeIndent(String coffeeIndent) {
		this.coffeeIndent = coffeeIndent;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}


}
