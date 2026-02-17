package com.dtt.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_rate_card_custom_pricing")
public class SystemRateCardCustomPricing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "stakeholder_type", nullable = false, length = 100)
	private String stakeHolderType;
	
	@Column(name = "stakeholder_id", nullable = false, length = 100)
	private String stakeHolderId;

	@Column(name = "stakeholder_name", nullable = false, length = 100)
	private String stakeHolderName;

	//@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "system_service_pk_fk", nullable = false)
	private SystemRateCard systemService;

	@Column(name = "discount_percentage", nullable = false, precision = 10, scale = 2)
	private BigDecimal discountPercentage;
	
	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStakeHolderType() {
		return stakeHolderType;
	}

	public void setStakeHolderType(String stakeHolderType) {
		this.stakeHolderType = stakeHolderType;
	}

	public String getStakeHolderId() {
		return stakeHolderId;
	}

	public void setStakeHolderId(String stakeHolderId) {
		this.stakeHolderId = stakeHolderId;
	}

	public String getStakeHolderName() {
		return stakeHolderName;
	}

	public void setStakeHolderName(String stakeHolderName) {
		this.stakeHolderName = stakeHolderName;
	}

	public SystemRateCard getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemRateCard systemService) {
		this.systemService = systemService;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "SystemRateCardCustomPricing [id=" + id + ", stakeHolderType=" + stakeHolderType + ", stakeHolderId="
				+ stakeHolderId + ", stakeHolderName=" + stakeHolderName + ", systemService=" + systemService
				+ ", discountPercentage=" + discountPercentage + ", isActive=" + isActive + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}
	
}
