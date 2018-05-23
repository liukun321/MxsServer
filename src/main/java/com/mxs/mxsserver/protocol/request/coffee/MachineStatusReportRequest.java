package com.mxs.mxsserver.protocol.request.coffee;

import com.mxs.mxsserver.protocol.ServiceID;
import com.mxs.mxsserver.protocol.enums.ICoffeeService;
import com.mxs.mxsserver.protocol.pack.PackIndex;
import com.mxs.mxsserver.protocol.pack.Unpack;
import com.mxs.mxsserver.protocol.request.Request;
import com.mxs.mxsserver.protocol.request.RequestID;

@RequestID(service = ServiceID.SVID_LITE_COFFEE, command = { ICoffeeService.CommandId.MACHINE_STATUS_REPORT
		+ "" })
public class MachineStatusReportRequest extends Request {
	@PackIndex(1)
	private long timestamp;//时间戳
	@PackIndex(2)
	private String machineStatusJson;
	@PackIndex(3)
	private int status;//咖啡机状态
	@PackIndex(4)
	private boolean is_running;//是否正常运行
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		timestamp = unpack.popLong();
		machineStatusJson = unpack.popVarstr();
		status = unpack.popInt();
		is_running = unpack.popBoolean();
		return null;
	}
	public void setMachineStatusJson(String machineStatusJson) {
		this.machineStatusJson = machineStatusJson;
	}
	public String getMachineStatusJson() {
		return machineStatusJson;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public boolean isIs_running() {
		return is_running;
	}
	public void setIs_running(boolean is_running) {
		this.is_running = is_running;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}