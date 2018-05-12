package com.mxs.mxsserver.protocol.responce;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ILinkService;


public class KeepAliveResponce extends Responce {

	public KeepAliveResponce(String uid) {
		super(uid);
		// TODO Auto-generated constructor stub
	}


	@Override
	public short getServiceId() {
		return ServiceID.SVID_LITE_LINK;
	}

	@Override
	public short getCommandId() {
		return ILinkService.CommandId.CID_REQUEST_HEARTBEAT;
	}

}
