package com.dtt.service.iface;

import org.springframework.data.domain.Page;

import com.dtt.requestdto.CreateCustomPricingRequest;
import com.dtt.requestdto.ServiceProviderCustomPricingFilterRequest;
import com.dtt.requestdto.UpdateCustomPricingRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.ServiceProviderCustomPricingResponse;

public interface CustomPricingIface {

	ApiResponse<?> createCustomPricing(CreateCustomPricingRequest request);

	ApiResponse<?> updateCustomPricing(Long id, UpdateCustomPricingRequest request);

	ApiResponse<?> deactivateCustomPricing(Long id);

	ApiResponse<?> activateCustomPricing(Long id);

	ApiResponse<?> getCustomRateCard();
	
	ApiResponse<Page<ServiceProviderCustomPricingResponse>> search(ServiceProviderCustomPricingFilterRequest request);

}
