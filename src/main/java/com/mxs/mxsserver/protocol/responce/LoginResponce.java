package com.mxs.mxsserver.protocol.responce;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.IAuthService;
import com.mxs.mxsserver.protocol.pack.Pack;

public class LoginResponce extends Responce {

	private short status;
	
	private String sessionId;
    
    private String vendorName;
	
	public LoginResponce(String uid) {
		super(uid);
	}

	@Override
	public Pack packResponce() {
		Pack p = new Pack();
		p.putShort(status);
		p.putVarstr(sessionId);
		p.putVarstr(vendorName);
		return p;
	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_AUTH;
	}

	@Override
	public short getCommandId() {
		return IAuthService.CommandId.CID_LOGIN;
	}

	public short getstatus() {
		return status;
	}

	public void setstatus(short status){
		this.status = status;
	}
	
	public String getsessionId(){
		return sessionId;
	}
	public void setsessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getvendorname(String vendorname){
		return vendorname;
	}
	public void setvendorname(String vendorname){
		this.vendorName = vendorname;
	}
}
