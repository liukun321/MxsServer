package com.mxs.mxsserver.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 咖啡机信息
 * @author liukun
 *
 */
@Entity
public class CoffeeMachine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4997312329115966677L;
	@Id
	@Column(length = 100)
	private String machineId;
//	@NotNull
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer number;
	
	private String machineInfo;
	/**
	 * 咖啡机的状态
	 * 0:闲置状态；
	  * 1：清洗阶段；
	  * 2：清洗完成；
	  * 3：缺料状态；
	  * 4：填料完成；
	  * 5：填料未完成；
	  * 6：支付阶段
	  * 7：支付成功
	  * 8：支付失败
	  * 9：二维码传输
	  * 10：磨豆机启动
	  * 11：冲泡状态
	  * 12：加粉料状态
	  * 13：加辅料状态
	 * 
	 */
	@NotNull
	private Integer status;
	/**
	 * 咖啡机的是否正常运行
	 */
	@NotNull
	private Boolean is_running;
	/**
	 * 咖啡机设立时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 咖啡机 物料更新时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 咖啡机修复时间, 即重新正常运行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repaireTime;
	/**
	 * 物料信息
	 */
//	@ManyToMany
//	@JoinTable(joinColumns = { @JoinColumn(name = "coffee_machineCode") }, inverseJoinColumns = {
//            @JoinColumn(name = "material_category") }) //被控方表字段名
//	@ElementCollection
//	@NotNull
//	private List<Material> stackBoxes;
	/**
	 * 咖啡机负责的运维人员编号,在创建时允许为空
	 */
//	@ManyToOne(optional = false)
	private String employeeCode;
	public String getMachineId() {
		return machineId;
	}
	public Integer getNumber() {
		return number;
	}
	public String getMachineInfo() {
		return machineInfo;
	}
	public Integer getStatus() {
		return status;
	}
	public Boolean getIs_running() {
		return is_running;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Date getRepaireTime() {
		return repaireTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public void setMachineInfo(String machineInfo) {
		this.machineInfo = machineInfo;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setRepaireTime(Date repaireTime) {
		this.repaireTime = repaireTime;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
		result = prime * result + ((is_running == null) ? 0 : is_running.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((machineInfo == null) ? 0 : machineInfo.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((repaireTime == null) ? 0 : repaireTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		CoffeeMachine other = (CoffeeMachine) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		if (is_running == null) {
			if (other.is_running != null)
				return false;
		} else if (!is_running.equals(other.is_running))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (machineInfo == null) {
			if (other.machineInfo != null)
				return false;
		} else if (!machineInfo.equals(other.machineInfo))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (repaireTime == null) {
			if (other.repaireTime != null)
				return false;
		} else if (!repaireTime.equals(other.repaireTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CoffeeMachine [machineId=" + machineId + ", number=" + number + ", machineInfo=" + machineInfo
				+ ", status=" + status + ", is_running=" + is_running + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", repaireTime=" + repaireTime + ", employeeCode=" + employeeCode + "]";
	}
	
	
}
