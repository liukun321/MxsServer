package com.mxs.mxsserver.vo.result;

import java.util.List;

public class MachineInfoVo {
	//咖啡机的ID
	private String machineId;
	//咖啡机的版本号
	private Integer machineVersion;
	
	private Integer status;
	
	private List<InfoData> list;
	
	public Integer getStatus() {
		return status;
	}
	public List<InfoData> getList() {
		return list;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setList(List<InfoData> list) {
		this.list = list;
	}
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
