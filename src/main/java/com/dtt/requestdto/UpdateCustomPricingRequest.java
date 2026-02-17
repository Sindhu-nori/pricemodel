package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateCustomPricingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("stakeHolderName")
	private String stakeHolderName;
	
	@JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;

	public String getStakeHolderName() {
		return stakeHolderName;
	}

	public void setStakeHolderName(String stakeHolderName) {
		this.stakeHolderName = stakeHolderName;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setSpecialPricePerTransaction(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	@Override
	public String toString() {
		return "[stakeHolderName=" + stakeHolderName + ", discountPercentage="
				+ discountPercentage + "]";
	}
    
}
