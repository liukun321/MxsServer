package com.mxs.mxsserver.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠券
 * @author liukun
 *
 */
@Entity
public class Coupons implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3268157264501194130L;
	@Id
	private Integer id;
	private String value;//优惠金额
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	@NotNull
	private String couponCode;
	private String discount;
	public Integer getId() {
		return id;
	}
	public String getValue() {
		return value;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public String getDiscount() {
		return discount;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couponCode == null) ? 0 : couponCode.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Coupons other = (Coupons) obj;
		if (couponCode == null) {
			if (other.couponCode != null)
				return false;
		} else if (!couponCode.equals(other.couponCode))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Coupons [id=" + id + ", value=" + value + ", endTime=" + endTime + ", couponCode=" + couponCode
				+ ", discount=" + discount + "]";
	}
	
}
