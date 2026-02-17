package com.dtt.responsedto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RateCardResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SystemServiceResponseDto systemService;
    private Integer minVolume;
    private Integer maxVolume;
    
    private BigDecimal discountPercentage;
    
	public SystemServiceResponseDto getSystemService() {
		return systemService;
	}
	public void setSystemService(SystemServiceResponseDto systemService) {
		this.systemService = systemService;
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
	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	@Override
	public String toString() {
		return "[systemService=" + systemService + ", minVolume=" + minVolume + ", maxVolume="
				+ maxVolume + ", discountPercentage=" + discountPercentage + "]";
	}
	

}
