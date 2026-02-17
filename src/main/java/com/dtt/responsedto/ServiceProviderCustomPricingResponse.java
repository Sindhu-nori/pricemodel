package com.dtt.responsedto;

import java.math.BigDecimal;

public class ServiceProviderCustomPricingResponse {

    private Long id;

    private String organizationId;
    private String organizationName;

    private Long serviceId;
    private String serviceName;
    private String serviceCode;

    private BigDecimal basePrice;
    private BigDecimal discountPercentage;
    private BigDecimal priceAfterDiscount;


	public ServiceProviderCustomPricingResponse() {
	}

	public ServiceProviderCustomPricingResponse(
            Long id,
            String organizationId,
            String organizationName,
            Long serviceId,
            String serviceName,
            String serviceCode,
            BigDecimal basePrice,
            BigDecimal discountPercentage,
            BigDecimal priceAfterDiscount) {

        this.id = id;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.basePrice = basePrice;
        this.discountPercentage = discountPercentage;
        this.priceAfterDiscount = priceAfterDiscount;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public BigDecimal getPriceAfterDiscount() {
		return priceAfterDiscount;
	}

	public void setPriceAfterDiscount(BigDecimal priceAfterDiscount) {
		this.priceAfterDiscount = priceAfterDiscount;
	}
    
}

