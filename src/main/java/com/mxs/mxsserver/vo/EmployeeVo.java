package com.mxs.mxsserver.vo;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EmployeeVo {
//	@NotEmpty
	private String employeeCode;
	@NotEmpty
	private String employeeName;
	@NotEmpty
	private String password;
	private Set<String> venderNames;
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public String getPassword() {
		return password;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getVenderNames() {
		return venderNames;
	}
	public void setVenderNames(Set<String> venderNames) {
		this.venderNames = venderNames;
	}
	@Override
	public String toString() {
		return "EmployeeVo [employeeCode=" + employeeCode + ", employeeName=" + employeeName + ", password=" + password
				+ ", venderNames=" + venderNames + "]";
	}
	
	
}
