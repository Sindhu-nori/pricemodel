package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateSystemServiceDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("projectId")
	private String project;
	
	@JsonProperty("serviceCode")
	private String serviceCode;
	
	@JsonProperty("serviceName")
	private String serviceName;
	
	@JsonProperty("serviceType")
	private String serviceType;
	
	@JsonProperty("pricePerTransaction")
	private BigDecimal pricePerTransaction;
	
	@JsonProperty("platformFeePercent")
	private BigDecimal platformFeePercent;
	
	@JsonProperty("governmentVatPercent")
	private BigDecimal governmentVatPercent;
	
	@JsonProperty("vatIncludedInPricing")
	private boolean vatIncludedInPricing;
	
	@JsonProperty("isActive")
	private boolean isActive;
	
	@JsonProperty("rateEffectiveFrom")
    private String rateEffectiveFrom;

	@JsonProperty("rateEffectiveUpto")
    private String rateEffectiveUpto;
	
	@JsonProperty("rateCards")
	private List<CreateRateCardDto> rateCards;
	
	public String getProject() {
		return project;
	}
	public void setProjectId(String project) {
		this.project = project;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public BigDecimal getPricePerTransaction() {
		return pricePerTransaction;
	}
	public void setPricePerTransaction(BigDecimal pricePerTransaction) {
		this.pricePerTransaction = pricePerTransaction;
	}
	public BigDecimal getPlatformFeePercent() {
		return platformFeePercent;
	}
	public void setPlatformFeePercent(BigDecimal platformFeePercent) {
		this.platformFeePercent = platformFeePercent;
	}
	public BigDecimal getGovernmentVatPercent() {
		return governmentVatPercent;
	}
	public void setGovernmentVatPercent(BigDecimal governmentVatPercent) {
		this.governmentVatPercent = governmentVatPercent;
	}
	public boolean getVatIncludedInPricing() {
		return vatIncludedInPricing;
	}
	public void setVatIncludedInPricing(boolean vatIncludedInPricing) {
		this.vatIncludedInPricing = vatIncludedInPricing;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<CreateRateCardDto> getRateCards() {
		return rateCards;
	}
	public void setRateCards(List<CreateRateCardDto> rateCards) {
		this.rateCards = rateCards;
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
		return " [project=" + project + ", serviceCode=" + serviceCode + ", serviceName="
				+ serviceName + ", serviceType=" + serviceType + ", pricePerTransaction=" + pricePerTransaction
				+ ", platformFeePercent=" + platformFeePercent + ", governmentVatPercent=" + governmentVatPercent
				+ ", vatIncludedInPricing=" + vatIncludedInPricing + ", isActive=" + isActive + ", rateEffectiveFrom="
				+ rateEffectiveFrom + ", rateEffectiveUpto=" + rateEffectiveUpto + ", rateCards=" + rateCards + "]";
	}
	
}
