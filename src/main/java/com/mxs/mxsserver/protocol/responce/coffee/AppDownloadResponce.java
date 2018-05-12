package com.mxs.mxsserver.protocol.responce.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.Pack;
import com.mxs.mxsserver.protocol.responce.Responce;

public class AppDownloadResponce extends Responce {
	private String iosDownloadUrl;
	
	private String androidDownloadUrl;
	
	public AppDownloadResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public Pack packResponce() {
        Pack pack = new Pack();
        pack.putVarstr(iosDownloadUrl);
        pack.putVarstr(androidDownloadUrl);
        return pack;
    }
	public String getAndroidDownloadUrl() {
		return androidDownloadUrl;
	}
	public void setAndroidDownloadUrl(String androidDownloadUrl) {
		this.androidDownloadUrl = androidDownloadUrl;
	}
	public String getIosDownloadUrl() {
		return iosDownloadUrl;
	}
	public void setIosDownloadUrl(String iosDownloadUrl) {
		this.iosDownloadUrl = iosDownloadUrl;
	}

	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_COFFEE;
	}

	@Override
	public short getCommandId() {
		return ICoffeeService.CommandId.APP_DOWNLOAD;
	}
}
