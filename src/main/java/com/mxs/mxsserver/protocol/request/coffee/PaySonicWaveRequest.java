package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.PAY_ALI_SONICWAVE
		+ "" })
public class PaySonicWaveRequest extends SingleRequest {

	@PackIndex(0)
	private int coffeeId;
	@PackIndex(1)
	private String dosing;
	@PackIndex(2)
	private short provider;
	@PackIndex(3)
	private String dynamicID;
	
	
}
