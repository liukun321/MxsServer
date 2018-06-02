package com.mxs.mxsserver.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CoffeeInfoVo {
	@NotEmpty
	private String coffeeName;
	@NotEmpty
	private Double price;
	@NotEmpty
	private String imgurl;
	private Boolean is_new = false;
	@NotEmpty
	private Boolean is_hot;
	
	@NotEmpty
	private Boolean isSugar;
	/**
	 * 折后价
	 */
	@NotEmpty
	private Double discount_price;
	/**
	 * 是否打折
	 */
	@NotEmpty
	private Boolean discount;
	@Override
	public String toString() {
		return "CoffeeInfoVo [coffeeName=" + coffeeName + ", price=" + price + ", imgurl=" + imgurl + ", is_new="
				+ is_new + ", is_hot=" + is_hot + ", isSugar=" + isSugar + ", discount_price=" + discount_price
				+ ", discount=" + discount + "]";
	}

}
