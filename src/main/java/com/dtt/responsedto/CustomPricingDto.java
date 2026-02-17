package com.dtt.responsedto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomPricingDto implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stakeholderType;
    private String stakeholderName;
    private String stakeholderId;
    private BigDecimal specialPricePerTransaction;
    private boolean isActive;

	public CustomPricingDto() {
	}

	public CustomPricingDto(String stakeholderType, String stakeholderName, String stakeholderId,
							BigDecimal specialPricePerTransaction, boolean isActive) {
		super();
		this.stakeholderType = stakeholderType;
		this.stakeholderName = stakeholderName;
		this.stakeholderId = stakeholderId;
		this.specialPricePerTransaction = specialPricePerTransaction;
		this.isActive = isActive;
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
	public BigDecimal getSpecialPricePerTransaction() {
		return specialPricePerTransaction;
	}
	public void setSpecialPricePerTransaction(BigDecimal specialPricePerTransaction) {
		this.specialPricePerTransaction = specialPricePerTransaction;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "[stakeholderType=" + stakeholderType + ", stakeholderName=" + stakeholderName
				+ ", stakeholderId=" + stakeholderId + ", specialPricePerTransaction=" + specialPricePerTransaction
				+ ", isActive=" + isActive + "]";
	}

}
