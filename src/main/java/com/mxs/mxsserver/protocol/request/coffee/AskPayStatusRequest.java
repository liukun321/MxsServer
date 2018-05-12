package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.ASK_PAY_STATUS
		+ "" })
public class AskPayStatusRequest extends Request {
	@PackIndex(0)
	private String coffeeindent;
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeindent = unpack.popVarstr();
		return null;
	}

	public String getCoffeeindent() {
		return coffeeindent;
	}
	public void setCoffeeindent(String coffeeindent) {
		this.coffeeindent = coffeeindent;
	}
}
