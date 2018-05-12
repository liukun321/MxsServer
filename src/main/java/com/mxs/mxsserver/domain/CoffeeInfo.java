package com.mxs.mxsserver.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "coffeeinfo")
public class CoffeeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6842830008281182067L;
	@Id
	private Integer coffeeId;
	@NotNull
	private String coffeeName;
	@NotNull
	private Double price;
	private Double discount;
	private String imgurl;
//	@NotNull
//	private Integer soldnum;
//	private Double volumn;
	private Boolean is_new;
	
	private Boolean is_hot;
	@NotNull
	private Boolean isSugar;

	public Integer getCoffeeId() {
		return coffeeId;
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

	public void setCoffeeId(Integer coffeeId) {
		this.coffeeId = coffeeId;
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

	public String getCoffeeName() {
		return coffeeName;
	}

	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}

	public Boolean getIsSugar() {
		return isSugar;
	}

	public void setIsSugar(Boolean isSugar) {
		this.isSugar = isSugar;
	}

	public Boolean getIs_new() {
		return is_new;
	}

	public Boolean getIs_hot() {
		return is_hot;
	}

	public void setIs_new(Boolean is_new) {
		this.is_new = is_new;
	}

	public void setIs_hot(Boolean is_hot) {
		this.is_hot = is_hot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coffeeId == null) ? 0 : coffeeId.hashCode());
		result = prime * result + ((coffeeName == null) ? 0 : coffeeName.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((imgurl == null) ? 0 : imgurl.hashCode());
		result = prime * result + ((isSugar == null) ? 0 : isSugar.hashCode());
		result = prime * result + ((is_hot == null) ? 0 : is_hot.hashCode());
		result = prime * result + ((is_new == null) ? 0 : is_new.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		CoffeeInfo other = (CoffeeInfo) obj;
		if (coffeeId == null) {
			if (other.coffeeId != null)
				return false;
		} else if (!coffeeId.equals(other.coffeeId))
			return false;
		if (coffeeName == null) {
			if (other.coffeeName != null)
				return false;
		} else if (!coffeeName.equals(other.coffeeName))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (imgurl == null) {
			if (other.imgurl != null)
				return false;
		} else if (!imgurl.equals(other.imgurl))
			return false;
		if (isSugar == null) {
			if (other.isSugar != null)
				return false;
		} else if (!isSugar.equals(other.isSugar))
			return false;
		if (is_hot == null) {
			if (other.is_hot != null)
				return false;
		} else if (!is_hot.equals(other.is_hot))
			return false;
		if (is_new == null) {
			if (other.is_new != null)
				return false;
		} else if (!is_new.equals(other.is_new))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CoffeeInfo [coffeeId=" + coffeeId + ", coffeeName=" + coffeeName + ", price=" + price + ", discount="
				+ discount + ", imgurl=" + imgurl + ", is_new=" + is_new + ", is_hot=" + is_hot + ", isSugar=" + isSugar
				+ "]";
	}

}
