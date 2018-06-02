package com.mxs.mxsserver.protocol.request;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ILinkService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;


@RequestID(service = ServiceID.SVID_LITE_LINK, command = { ILinkService.CommandId.CID_REQUEST_HEARTBEAT
		+ "" })
public class KeepAliveRequest extends Request {
//	@PackIndex(1)
//	private boolean isrun;
//	@PackIndex(2)
//	private int type;
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
//		isrun = unpack.popBoolean();
//		type = unpack.popInt();
		return null;
	}

}
