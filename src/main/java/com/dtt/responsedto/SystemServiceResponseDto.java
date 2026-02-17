package com.dtt.responsedto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SystemServiceResponseDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String projectId;

    private String serviceType;

    private BigDecimal pricePerTransaction;
	// or BigDecimal
    private BigDecimal platformFeePercent;

    private BigDecimal governmentVatPercent;

    private boolean vatIncludedInPricing;

    private String serviceName;

    private String serviceCode;

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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	@Override
	public String toString() {
		return " [id=" + id + ", projectId=" + projectId + ", serviceType=" + serviceType
				+ ", pricePerTransaction=" + pricePerTransaction + ", platformFeePercent=" + platformFeePercent
				+ ", governmentVatPercent=" + governmentVatPercent + ", vatIncludedInPricing=" + vatIncludedInPricing
				+ ", serviceName=" + serviceName + ", serviceCode=" + serviceCode + "]";
	}


}
