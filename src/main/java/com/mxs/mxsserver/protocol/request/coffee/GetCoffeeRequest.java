package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.marshal.ArrayMable;
import com.mxs.mxsserver.protocol.marshal.Property;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.GET_COFFEE
		+ "" })

public class GetCoffeeRequest extends Request {

	public Unpack unpackBody(Unpack unpack) throws Exception {
	
		return null;
	}

	
}
