package com.mxs.mxsserver.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 运营人员
 * @author liukun
 *
 */
@Entity
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1166620198244266078L;
	/**
	 * 运维人员编号
	 */
	@Id
	private String employeeCode;
	@NotNull
	private String employeeName;
	@NotNull
	private String password;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date joinTime;
	/**
	 * 在创建运维人员时，必须分配责任内的咖啡机
	 * 是关联咖啡机的编号还是咖啡机的所有信息？？？？？
	 */
//	@OneToMany(mappedBy = "machineCode")
	@NotNull
	@ElementCollection
	@Column(name = "venderName")
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
	public Date getJoinTime() {
		return joinTime;
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
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public Set<String> getVenderNames() {
		return venderNames;
	}
	public void setVenderNames(Set<String> venderNames) {
		this.venderNames = venderNames;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
		result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result + ((joinTime == null) ? 0 : joinTime.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((venderNames == null) ? 0 : venderNames.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (joinTime == null) {
			if (other.joinTime != null)
				return false;
		} else if (!joinTime.equals(other.joinTime))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (venderNames == null) {
			if (other.venderNames != null)
				return false;
		} else if (!venderNames.equals(other.venderNames))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Employee [employeeCode=" + employeeCode + ", employeeName=" + employeeName
				+ ", password=" + password + ", joinTime=" + joinTime + ", venderNames=" + venderNames + "]";
	}
	
	//TODO 运维人员的其他基本信息待定
	
	
}
