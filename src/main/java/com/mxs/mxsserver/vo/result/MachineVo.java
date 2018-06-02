package com.mxs.mxsserver.vo.result;

public class MachineVo {
	//咖啡机的ID
	private String machineId;
	//咖啡机的版本号
	private Integer machineVersion;
	public String getMachineId() {
		return machineId;
	}
	public Integer getMachineVersion() {
		return machineVersion;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setMachineVersion(Integer machineVersion) {
		this.machineVersion = machineVersion;
	}
	@Override
	public String toString() {
		return "MachineVo [machineId=" + machineId + ", machineVersion=" + machineVersion + "]";
	}
	
}
