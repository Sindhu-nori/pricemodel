package com.dtt.requestdto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCustomPricingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("serviceId")
	private Long serviceId;

	@JsonProperty("stakeholderType")
    private String stakeholderType;
	
	@JsonProperty("stakeholderName")
    private String stakeholderName;
	
	@JsonProperty("stakeholderId")
    private String stakeholderId;

	@JsonProperty("discountPercentage")
    private BigDecimal discountPercentage;
	
	@JsonProperty("status")
    private boolean status;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getStakeholderType() {
		return stakeholderType;
	}
	public void setStakeholderType(String stakeholderType) {
		this.stakeholderType = stakeholderType;
	}
	public String getStakeholderName() {
		return stakeholderName;
	}
	public void setStakeholderName(String stakeholderName) {
		this.stakeholderName = stakeholderName;
	}
	public String getStakeholderId() {
		return stakeholderId;
	}
	public void setStakeholderId(String stakeholderId) {
		this.stakeholderId = stakeholderId;
	}
	
	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "[serviceId=" + serviceId + ", stakeholderType=" + stakeholderType
				+ ", stakeholderName=" + stakeholderName + ", stakeholderId=" + stakeholderId + ", discountPercentage="
				+ discountPercentage + ", status=" + status + "]";
	}
	
    


}
