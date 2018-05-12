package com.mxs.mxsserver.protocol.request;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ILinkService;
import com.mxs.mxsserver.protocol.pack.Unpack;


@RequestID(service = ServiceID.SVID_LITE_LINK, command = { ILinkService.CommandId.CID_HEARTBEAT
		+ "" })
public class KeepAliveRequest extends Request {

	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		return null;
	}

}
