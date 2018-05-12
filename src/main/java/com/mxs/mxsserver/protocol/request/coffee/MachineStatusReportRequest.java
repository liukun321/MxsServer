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
	private String location;//咖啡机位置
	@PackIndex(4)
	private String latitude;//咖啡机维度
	@PackIndex(5)
	private String longitude;//咖啡机经度
	@PackIndex(6)
	private boolean is_running;//是否正常运行
	
	@Override
	public Unpack unpackBody(Unpack unpack) throws Exception {
		timestamp = unpack.popLong();
		machineStatusJson = unpack.popVarstr();
		location = unpack.popVarstr();
		latitude = unpack.popVarstr();
		longitude = unpack.popVarstr();
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
	public String getLocation() {
		return location;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public boolean isIs_running() {
		return is_running;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setIs_running(boolean is_running) {
		this.is_running = is_running;
	}
	
	
}