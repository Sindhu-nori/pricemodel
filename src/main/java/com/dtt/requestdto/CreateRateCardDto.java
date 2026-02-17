package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRateCardDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@JsonProperty("minVolume")
	private Integer minVolume;

	@JsonProperty("maxVolume")
    private Integer maxVolume;

	@JsonProperty("pricePerTransaction")
    private BigDecimal pricePerTransaction;

	@JsonProperty("rateEffectiveFrom")
    private String rateEffectiveFrom; // "yyyy-MM-dd HH:mm:ss"
	

	@JsonProperty("rateEffectiveUpto")
    private String rateEffectiveUpto;

	public Integer getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(Integer minVolume) {
		this.minVolume = minVolume;
	}

	public Integer getMaxVolume() {
		return maxVolume;
	}

	public void setMaxVolume(Integer maxVolume) {
		this.maxVolume = maxVolume;
	}

	

	public BigDecimal getPricePerTransaction() {
		return pricePerTransaction;
	}

	public void setPricePerTransaction(BigDecimal pricePerTransaction) {
		this.pricePerTransaction = pricePerTransaction;
	}

	public String getRateEffectiveFrom() {
		return rateEffectiveFrom;
	}

	public void setRateEffectiveFrom(String rateEffectiveFrom) {
		this.rateEffectiveFrom = rateEffectiveFrom;
	}

	public String getRateEffectiveUpto() {
		return rateEffectiveUpto;
	}

	public void setRateEffectiveUpto(String rateEffectiveUpto) {
		this.rateEffectiveUpto = rateEffectiveUpto;
	}

	@Override
	public String toString() {
		return "[minVolume=" + minVolume + ", maxVolume=" + maxVolume + ", pricePerTransaction="
				+ pricePerTransaction + ", rateEffectiveFrom=" + rateEffectiveFrom + ", rateEffectiveUpto="
				+ rateEffectiveUpto + "]";
	} 

}
