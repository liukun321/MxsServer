package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.FECTH_COFFEE_BY_QRCODE
		+ "" })
public class VerifyQrcodeRequest extends SingleRequest {

	@PackIndex(0)
	private int coffeeId;
	@PackIndex(1)
	private String dosingContent;
	@PackIndex(2)
	private String coffeeIndent;
	@PackIndex(3)
	private String user;
	
	
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCoffeeIndent() {
		return coffeeIndent;
	}

	public void setCoffeeIndent(String coffeeIndent) {
		this.coffeeIndent = coffeeIndent;
	}
}
