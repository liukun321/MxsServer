package com.mxs.mxsserver.bean;

//咖啡品种信息及制作流程， 字段名与CoffeeInfo表中的字段对应
public class Coffee {
	private Integer coffeeId;
	private String coffeeName;
	private Double price;
	private Double discount;
	private String imgurl;
	private Boolean is_new;
	private Boolean is_hot;
	private Boolean isSugar;
	public Integer getCoffeeId() {
		return coffeeId;
	}
	public String getCoffeeName() {
		return coffeeName;
	}
	public Double getPrice() {
		return price;
	}
	public Double getDiscount() {
		return discount;
	}
	public String getImgurl() {
		return imgurl;
	}
	public Boolean getIs_new() {
		return is_new;
	}
	public Boolean getIs_hot() {
		return is_hot;
	}
	public Boolean getIsSugar() {
		return isSugar;
	}
	public void setCoffeeId(Integer coffeeId) {
		this.coffeeId = coffeeId;
	}
	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public void setIs_new(Boolean is_new) {
		this.is_new = is_new;
	}
	public void setIs_hot(Boolean is_hot) {
		this.is_hot = is_hot;
	}
	public void setIsSugar(Boolean isSugar) {
		this.isSugar = isSugar;
	}
	@Override
	public String toString() {
		return "Coffee [coffeeId=" + coffeeId + ", coffeeName=" + coffeeName + ", price=" + price + ", discount="
				+ discount + ", imgurl=" + imgurl + ", is_new=" + is_new + ", is_hot=" + is_hot + ", isSugar=" + isSugar
				+ "]";
	}
	
	
}