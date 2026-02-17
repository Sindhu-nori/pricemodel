package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.requestdto.CreateCustomPricingRequest;
import com.dtt.requestdto.ServiceProviderCustomPricingFilterRequest;
import com.dtt.requestdto.UpdateCustomPricingRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.ServiceProviderCustomPricingResponse;
import com.dtt.service.iface.CustomPricingIface;

@RestController
@RequestMapping("/api")
public class CustomPricingController {

	@Autowired
	CustomPricingIface customPricingIface;

	@PostMapping("/create/custom/ratecard")
	public ApiResponse<?> createCustomPricing(@RequestBody CreateCustomPricingRequest request) {
		return customPricingIface.createCustomPricing(request);
	}

	@PostMapping("/update/custom/ratecard/{id}")
	public ApiResponse<?> updateCustomPricing(@PathVariable("id") Long id,
			@RequestBody UpdateCustomPricingRequest request) {
		return customPricingIface.updateCustomPricing(id, request);
	}

	// 🔹 DEACTIVATE custom pricing
	@PostMapping("/deactive/custom/ratecard/{id}/deactivate")
	public ApiResponse<?> deactivateCustomPricing(@PathVariable("id") Long id) {
		return customPricingIface.deactivateCustomPricing(id);
	}

	@PostMapping("/activate/custom/ratecard/{id}/activate")
	public ApiResponse<?> activateCustomPricing(@PathVariable("id") Long id) {
		return customPricingIface.activateCustomPricing(id);
	}
	
	@GetMapping("/get/custom/ratecard")
	public ApiResponse<?> getCustomRateCard() {
		return customPricingIface.getCustomRateCard();
	}
	
	@PostMapping("/search")
    public ApiResponse<Page<ServiceProviderCustomPricingResponse>> search(
            @RequestBody ServiceProviderCustomPricingFilterRequest request) {

        return customPricingIface.search(request);
    }
	
}
