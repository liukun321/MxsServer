package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.MACHINE_STATUS_SERVER
		+ "" })
public class MachineStatusServerRequest extends Request {
	@PackIndex(0)
	private long timestamp;
	@PackIndex(1)
	private String machineInfo;
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		timestamp = unpack.popLong();
		machineInfo = unpack.popVarstr();
		return null;
	}
	public void setMachineInfo(String machineInfo) {
		this.machineInfo = machineInfo;
	}
	public String getMachineInfo() {
		return machineInfo;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getTimestamp() {
		return timestamp;
	}
}