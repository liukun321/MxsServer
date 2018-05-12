package com.mxs.mxsserver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Payindent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8294147088112637894L;
	/**
	 * 咖啡Id 与咖啡品种关联
	 */
	@Id
	@Column(length = 200)
	private String indentId;
	/**
	 * 咖啡机的Id
	 */
	@Column(nullable = false, length = 100)
	private String machineId;
	/**
	 * 订单详情,产品编号
	 */
	private String coffeeindent;
	/**
	 * 订单原价
	 */
	private Double priceOri;
	/**
	 * 订单价格
	 */
	@NotNull
	private Double price;
	/**
	 * 支付方式
	 * 0 支付方式错误
	 * 1 支付宝
	 * 2 微信
	 */
	private Integer payMethod;
	/**
	 * 支付状态
	 * 0:待支付
	 * 1：支付成功
	 * 2：支付失败
	 * 3:退款成功
	 * 4：退款失败
	 * 5：订单作废
	 */
	@Column(length = 11, nullable = false)
	private Integer payStatus;
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 支付订单号，支付宝或者微信
	 * @return
	 */
	private String orderId;
	/**
	 * true 热饮
	 * fals 冷饮
	 */
	private boolean isHot;
	/**
	 * 糖度
	 * 0 1 2 3 4
	 */
	private Integer sugar;
	
	public String getIndentId() {
		return indentId;
	}
	public String getCoffeeindent() {
		return coffeeindent;
	}
	public Double getPriceOri() {
		return priceOri;
	}
	public Double getPrice() {
		return price;
	}
	public Integer getPayMethod() {
		return payMethod;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setCoffeeindent(String coffeeindent) {
		this.coffeeindent = coffeeindent;
	}
	public void setPriceOri(Double priceOri) {
		this.priceOri = priceOri;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public boolean isHot() {
		return isHot;
	}
	public Integer getSugar() {
		return sugar;
	}
	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}
	public void setSugar(Integer sugar) {
		this.sugar = sugar;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coffeeindent == null) ? 0 : coffeeindent.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((indentId == null) ? 0 : indentId.hashCode());
		result = prime * result + (isHot ? 1231 : 1237);
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((payMethod == null) ? 0 : payMethod.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((priceOri == null) ? 0 : priceOri.hashCode());
		result = prime * result + ((sugar == null) ? 0 : sugar.hashCode());
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
		Payindent other = (Payindent) obj;
		if (coffeeindent == null) {
			if (other.coffeeindent != null)
				return false;
		} else if (!coffeeindent.equals(other.coffeeindent))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (indentId == null) {
			if (other.indentId != null)
				return false;
		} else if (!indentId.equals(other.indentId))
			return false;
		if (isHot != other.isHot)
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (payMethod == null) {
			if (other.payMethod != null)
				return false;
		} else if (!payMethod.equals(other.payMethod))
			return false;
		if (payStatus == null) {
			if (other.payStatus != null)
				return false;
		} else if (!payStatus.equals(other.payStatus))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (priceOri == null) {
			if (other.priceOri != null)
				return false;
		} else if (!priceOri.equals(other.priceOri))
			return false;
		if (sugar == null) {
			if (other.sugar != null)
				return false;
		} else if (!sugar.equals(other.sugar))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Payindent [indentId=" + indentId + ", machineId=" + machineId + ", coffeeindent=" + coffeeindent
				+ ", priceOri=" + priceOri + ", price=" + price + ", payMethod=" + payMethod + ", payStatus="
				+ payStatus + ", createTime=" + createTime + ", orderId=" + orderId + ", isHot=" + isHot + ", sugar="
				+ sugar + "]";
	}
	
	
}
