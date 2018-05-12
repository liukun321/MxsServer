package com.mxs.mxsserver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Material implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 796004993114259575L;

	@Id
//	@Column(length = 50)
	private String machineId;
	
	private Double water;
	private Double cupnum;
	
	private Double milk;
	private Double sugar;
	
	private Double chocolate;
	private Double milktea;
	
	private Double coffeebean;

	public String getMachineId() {
		return machineId;
	}

	public Double getWater() {
		return water;
	}

	public Double getCupnum() {
		return cupnum;
	}

	public Double getMilk() {
		return milk;
	}

	public Double getSugar() {
		return sugar;
	}

	public Double getChocolate() {
		return chocolate;
	}

	public Double getMilktea() {
		return milktea;
	}

	public Double getCoffeebean() {
		return coffeebean;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public void setCupnum(Double cupnum) {
		this.cupnum = cupnum;
	}

	public void setMilk(Double milk) {
		this.milk = milk;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	public void setChocolate(Double chocolate) {
		this.chocolate = chocolate;
	}

	public void setMilktea(Double milktea) {
		this.milktea = milktea;
	}

	public void setCoffeebean(Double coffeebean) {
		this.coffeebean = coffeebean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chocolate == null) ? 0 : chocolate.hashCode());
		result = prime * result + ((coffeebean == null) ? 0 : coffeebean.hashCode());
		result = prime * result + ((cupnum == null) ? 0 : cupnum.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((milk == null) ? 0 : milk.hashCode());
		result = prime * result + ((milktea == null) ? 0 : milktea.hashCode());
		result = prime * result + ((sugar == null) ? 0 : sugar.hashCode());
		result = prime * result + ((water == null) ? 0 : water.hashCode());
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
		Material other = (Material) obj;
		if (chocolate == null) {
			if (other.chocolate != null)
				return false;
		} else if (!chocolate.equals(other.chocolate))
			return false;
		if (coffeebean == null) {
			if (other.coffeebean != null)
				return false;
		} else if (!coffeebean.equals(other.coffeebean))
			return false;
		if (cupnum == null) {
			if (other.cupnum != null)
				return false;
		} else if (!cupnum.equals(other.cupnum))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (milk == null) {
			if (other.milk != null)
				return false;
		} else if (!milk.equals(other.milk))
			return false;
		if (milktea == null) {
			if (other.milktea != null)
				return false;
		} else if (!milktea.equals(other.milktea))
			return false;
		if (sugar == null) {
			if (other.sugar != null)
				return false;
		} else if (!sugar.equals(other.sugar))
			return false;
		if (water == null) {
			if (other.water != null)
				return false;
		} else if (!water.equals(other.water))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Material [machineId=" + machineId + ", water=" + water + ", cupnum=" + cupnum + ", milk=" + milk
				+ ", sugar=" + sugar + ", chocolate=" + chocolate + ", milktea=" + milktea + ", coffeebean="
				+ coffeebean + "]";
	}

	
	}
