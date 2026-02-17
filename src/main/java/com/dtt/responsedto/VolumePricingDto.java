package com.dtt.responsedto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VolumePricingDto{

	private Integer minVolume;
    private Integer maxVolume;
    private BigDecimal pricePerTransaction;

	public VolumePricingDto() {
	}

	public VolumePricingDto(Integer minVolume, Integer maxVolume, BigDecimal pricePerTransaction) {
		super();
		this.minVolume = minVolume;
		this.maxVolume = maxVolume;
		this.pricePerTransaction = pricePerTransaction;

	}
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
	@Override
	public String toString() {
		return "VolumePricingDto [minVolume=" + minVolume + ", maxVolume=" + maxVolume + ", pricePerTransaction="
				+ pricePerTransaction + "]";
	}
}
