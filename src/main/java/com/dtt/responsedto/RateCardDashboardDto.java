package com.dtt.responsedto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class RateCardDashboardDto implements Serializable {


	private ServiceDto service;
    private List<VolumePricingDto> volumePricing;
    private List<CustomPricingDto> customPricing;

	public RateCardDashboardDto() {
	}

	public RateCardDashboardDto(ServiceDto service, List<VolumePricingDto> volumePricing,
								List<CustomPricingDto> customPricing) {
		super();
		this.service = service;
		this.volumePricing = volumePricing;
		this.customPricing = customPricing;
	}
	public ServiceDto getService() {
		return service;
	}
	public void setService(ServiceDto service) {
		this.service = service;
	}
	public List<VolumePricingDto> getVolumePricing() {
		return volumePricing;
	}
	public void setVolumePricing(List<VolumePricingDto> volumePricing) {
		this.volumePricing = volumePricing;
	}
	public List<CustomPricingDto> getCustomPricing() {
		return customPricing;
	}
	public void setCustomPricing(List<CustomPricingDto> customPricing) {
		this.customPricing = customPricing;
	}
	@Override
	public String toString() {
		return "[service=" + service + ", volumePricing=" + volumePricing + ", customPricing="
				+ customPricing + "]";
	}

}
