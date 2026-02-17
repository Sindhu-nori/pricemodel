package com.dtt.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_rate_card")
public class SystemRateCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	//@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "client_project_id", nullable = false)
	private ClientProjects project;


	@Column(name = "service_code")
	private String serviceCode;

	@Column(name = "Service_name")
	private String serviceName;

	@Column(name = "service_type")
	private String serviceType;

	@Column(name = "price_per_transaction")
	private BigDecimal pricePerTransaction;

	@Column(name = "platform_fee_percent")
	private BigDecimal platformFeePercent;

	@Column(name = "government_vat_percent")
	private BigDecimal governmentVatPercent;

	@Column(name = "vat_included_in_pricing")
	private boolean vatIncludedInPricing;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "rate_effective_from")
	private LocalDateTime rateEffectiveFrom;

	@Column(name = "rate_effective_upto")
	private LocalDateTime rateEffectiveUpto;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@Column(name = "updated_on")
	private LocalDateTime updatedOn;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ClientProjects getProject() {
		return project;
	}

	public void setProject(ClientProjects project) {
		this.project = project;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		return "{" +
				"id=" + id +
				", project=" + project +
				", serviceCode='" + serviceCode + '\'' +
				", serviceName='" + serviceName + '\'' +
				", serviceType='" + serviceType + '\'' +
				", pricePerTransaction=" + pricePerTransaction +
				", platformFeePercent=" + platformFeePercent +
				", governmentVatPercent=" + governmentVatPercent +
				", vatIncludedInPricing=" + vatIncludedInPricing +
				", isActive=" + isActive +
				", rateEffectiveFrom=" + rateEffectiveFrom +
				", rateEffectiveUpto=" + rateEffectiveUpto +
				", createdOn=" + createdOn +
				", updatedOn=" + updatedOn +
				'}';
	}
}
