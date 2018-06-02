package com.mxs.mxsserver.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class CoffeeList {
	@Id
	private String coffeeId;
	/**
	 * 咖啡的糖度
	 */
	private Integer sugar;
	/**
	 * 咖啡的杯数
	 */
	private Integer cupNum;
	/**
	 * 咖啡原价
	 */
	private Double priceOri;
	/**
	 * 是否打折
	 */
	private boolean discount;
	/**
	 * 折扣价
	 */
	private Double discount_price;
	/**
	 * 咖啡实际价格
	 */
	private Double price;
	public Integer getSugar() {
		return sugar;
	}
	public Integer getCupNum() {
		return cupNum;
	}
	public Double getPriceOri() {
		return priceOri;
	}
	public boolean isDiscount() {
		return discount;
	}
	public Double getDiscount_price() {
		return discount_price;
	}
	public Double getPrice() {
		return price;
	}
	public void setSugar(Integer sugar) {
		this.sugar = sugar;
	}
	public void setCupNum(Integer cupNum) {
		this.cupNum = cupNum;
	}
	public void setPriceOri(Double priceOri) {
		this.priceOri = priceOri;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public void setDiscount_price(Double discount_price) {
		this.discount_price = discount_price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCoffeeId() {
		return coffeeId;
	}
	public void setCoffeeId(String coffeeId) {
		this.coffeeId = coffeeId;
	}
	@Override
	public String toString() {
		return "CoffeeList [coffeeId=" + coffeeId + ", sugar=" + sugar + ", cupNum=" + cupNum + ", priceOri=" + priceOri
				+ ", discount=" + discount + ", discount_price=" + discount_price + ", price=" + price + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coffeeId == null) ? 0 : coffeeId.hashCode());
		result = prime * result + ((cupNum == null) ? 0 : cupNum.hashCode());
		result = prime * result + (discount ? 1231 : 1237);
		result = prime * result + ((discount_price == null) ? 0 : discount_price.hashCode());
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
		CoffeeList other = (CoffeeList) obj;
		if (coffeeId == null) {
			if (other.coffeeId != null)
				return false;
		} else if (!coffeeId.equals(other.coffeeId))
			return false;
		if (cupNum == null) {
			if (other.cupNum != null)
				return false;
		} else if (!cupNum.equals(other.cupNum))
			return false;
		if (discount != other.discount)
			return false;
		if (discount_price == null) {
			if (other.discount_price != null)
				return false;
		} else if (!discount_price.equals(other.discount_price))
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
	
	
}
