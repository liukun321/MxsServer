package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.RequestID;
import com.mxs.mxsserver.protocol.request.SingleRequest;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.VERIFY_CODE
		+ "" })
public class VerifyCoffeeRequest extends SingleRequest {
	@PackIndex(0)
	private String coffeeIndent; 
	@PackIndex(1)
	private long timestamp;
	
	public Unpack unpackBody(Unpack unpack) throws Exception {
		coffeeIndent = unpack.popVarstr();
		timestamp = unpack.popLong();
        return null;
    }
	public void setCoffeeIndent(String coffeeIndent) {
		this.coffeeIndent = coffeeIndent;
	}
	public String getCoffeeIndent() {
		return coffeeIndent;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getTimestamp() {
		return timestamp;
	}
}
