package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class ServiceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
   // private String projectId;
    
    private String projectId;
    private String serviceCode;
    private String serviceName;
    private String serviceType;
    private BigDecimal basePrice;
    private BigDecimal platformFeePercent;
    private BigDecimal governmentVatPercent;
    private boolean vatIncludedInPricing;

    private LocalDateTime rateEffectiveFrom;

    private LocalDateTime rateEffectiveUpto;
    private boolean isActive;

	public ServiceDto() {
	}

	public ServiceDto(Long id, String projectId, String serviceCode, String serviceName, String serviceType,
					  BigDecimal basePrice, BigDecimal platformFeePercent, BigDecimal governmentVatPercent,
					  boolean vatIncludedInPricing, LocalDateTime rateEffectiveFrom, LocalDateTime rateEffectiveUpto, boolean isActive) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.serviceCode = serviceCode;
		this.serviceName = serviceName;
		this.serviceType = serviceType;
		this.basePrice = basePrice;
		this.platformFeePercent = platformFeePercent;
		this.governmentVatPercent = governmentVatPercent;
		this.vatIncludedInPricing = vatIncludedInPricing;
		this.rateEffectiveFrom = rateEffectiveFrom;
		this.rateEffectiveUpto = rateEffectiveUpto;
		this.isActive = isActive;
	}
//	public ServiceDto(Long id, String projectId, String serviceCode, String serviceName, String serviceType,
//			BigDecimal basePrice, BigDecimal platformFeePercent, BigDecimal governmentVatPercent,
//			Boolean vatIncludedInPricing, String rateEffectiveFrom, String rateEffectiveUpto, Boolean isActive) {
//		super();
//		this.id = id;
//		this.projectId = projectId;
//		this.serviceCode = serviceCode;
//		this.serviceName = serviceName;
//		this.serviceType = serviceType;
//		this.basePrice = basePrice;
//		this.platformFeePercent = platformFeePercent;
//		this.governmentVatPercent = governmentVatPercent;
//		this.vatIncludedInPricing = vatIncludedInPricing;
//		this.rateEffectiveFrom = rateEffectiveFrom;
//		this.rateEffectiveUpto = rateEffectiveUpto;
//		this.isActive = isActive;
//	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
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
	public LocalDateTime getRateEffectiveFrom() {
		return rateEffectiveFrom;
	}
	public void setRateEffectiveFrom(LocalDateTime rateEffectiveFrom) {
		this.rateEffectiveFrom = rateEffectiveFrom;
	}
	public LocalDateTime getRateEffectiveUpto() {
		return rateEffectiveUpto;
	}
	public void setRateEffectiveUpto(LocalDateTime rateEffectiveUpto) {
		this.rateEffectiveUpto = rateEffectiveUpto;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", projectId=" + projectId + ", serviceCode=" + serviceCode + ", serviceName="
				+ serviceName + ", serviceType=" + serviceType + ", basePrice=" + basePrice + ", platformFeePercent="
				+ platformFeePercent + ", governmentVatPercent=" + governmentVatPercent + ", vatIncludedInPricing="
				+ vatIncludedInPricing + ", rateEffectiveFrom=" + rateEffectiveFrom + ", rateEffectiveUpto="
				+ rateEffectiveUpto + ", isActive=" + isActive + "]";
	}
	
    

}
