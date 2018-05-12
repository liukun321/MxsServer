package com.mxs.mxsserver.protocol.request;

import com.mxs.mxsserver.protocol.pack.Unpack;

public class SingleRequest extends Request {

	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		unpack(unpack);
		return null;
	}

}
