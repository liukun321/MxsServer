package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PICK_UP
		+ "" })
public class PickupRequest extends SingleRequest {

	@PackIndex(1)
	private String pickupCode;
//	@PackIndex(1)
//	private String dosing;
//	@PackIndex(2)
//	private short provider;
//	@PackIndex(3)
//	private String dynamicID;
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		pickupCode = unpack.popVarstr();
		return null;
	}
	public String getPickupCode() {
		return pickupCode;
	}
	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}
	
}
