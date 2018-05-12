package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class PayQrcodeCartResponce extends Responce {
	private String payIndent;

	private String coffeeIndents;

	private String price;

	private String priceOri;

	private String qrcodeUrl;
	
	public PayQrcodeCartResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putVarstr(payIndent);
        pack.putVarstr(coffeeIndents);
        pack.putVarstr(price);
        pack.putVarstr(priceOri);
        pack.putVarstr(qrcodeUrl);
       
        return pack;
    }

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.PAY_QRCODE_CART;
	}
	public String getCoffeeIndents() {
		return coffeeIndents;
	}
	public void setCoffeeIndents(String coffeeIndents) {
		this.coffeeIndents = coffeeIndents;
	}
	public String getPayIndent() {
		return payIndent;
	}
	public void setPayIndent(String payIndent) {
		this.payIndent = payIndent;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceOri() {
		return priceOri;
	}
	public void setPriceOri(String priceOri) {
		this.priceOri = priceOri;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	
}
