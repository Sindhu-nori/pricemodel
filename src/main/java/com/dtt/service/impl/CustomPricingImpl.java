package com.dtt.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtt.model.SystemRateCard;
import com.dtt.model.SystemRateCardCustomPricing;
import com.dtt.repo.SystemRateCardCustomPricingRepo;
import com.dtt.repo.SystemRateCardRepo;
import com.dtt.repo.SystemRateCardVolumesRepo;
import com.dtt.requestdto.CreateCustomPricingRequest;
import com.dtt.requestdto.ServiceProviderCustomPricingFilterRequest;
import com.dtt.requestdto.UpdateCustomPricingRequest;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.ServiceProviderCustomPricingResponse;
import com.dtt.service.iface.CustomPricingIface;
import com.dtt.utils.AppUtils;

@Service
public class CustomPricingImpl implements CustomPricingIface {

	private static final Logger log = LoggerFactory.getLogger(CustomPricingImpl.class);

	@Autowired
	SystemRateCardRepo servicesRepo;

	@Autowired
	SystemRateCardVolumesRepo rateCardRepo;

	@Autowired
	SystemRateCardCustomPricingRepo customerPricingRepo;

	// CREATE
	@Override
	public ApiResponse<?> createCustomPricing(CreateCustomPricingRequest request) {

		log.info("Create custom pricing request received. = {}", request);

		try {
			// 1️⃣ Validate service exists
			Optional<SystemRateCard> service = servicesRepo.findById(request.getServiceId());
			if (service.isEmpty()) {
				return new ApiResponse<>(false, "Service rate card not found", null);
			}

			// 2️⃣ Prevent duplicate custom pricing
			boolean exists = customerPricingRepo.existsBySystemServiceIdAndStakeHolderTypeAndStakeHolderId(
					request.getServiceId(), request.getStakeholderType(), request.getStakeholderId());

			if (exists) {
				log.warn("Duplicate custom pricing attempt. serviceId={}, stakeholderId={}", request.getServiceId(),
						request.getStakeholderId());
				return new ApiResponse<>(false, "Custom pricing already exists for this stakeholder and service", null);
			}

			// 3️⃣ Save custom pricing
			SystemRateCardCustomPricing pricing = new SystemRateCardCustomPricing();
			pricing.setSystemService(service.get());
			pricing.setStakeHolderType(request.getStakeholderType());
			pricing.setStakeHolderName(request.getStakeholderName());
			pricing.setStakeHolderId(request.getStakeholderId());
			pricing.setDiscountPercentage(request.getDiscountPercentage());
			pricing.setIsActive(request.getStatus());
			pricing.setCreatedOn(LocalDateTime.now());
			pricing.setUpdatedOn(AppUtils.getCurrentDateTime());

			SystemRateCardCustomPricing saved = customerPricingRepo.save(pricing);

			log.info("Custom pricing created successfully. id={}, serviceId={}, stakeholderId={}", saved.getId(),
					request.getServiceId(), request.getServiceId());

			return new ApiResponse<>(true, "Custom pricing created successfully", saved);

		} catch (IllegalArgumentException e) {
			log.warn("Business validation failed while creating custom pricing. reason={}", e.getMessage());
			// throw e;
		
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		} catch (Exception e) {
			log.error("Unexpected error while creating custom pricing. serviceId={}", request.getServiceId(), e);
			// throw e;
		
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		}
	}

	// UPDATE
	@Override
	public ApiResponse<?> updateCustomPricing(Long id, UpdateCustomPricingRequest request) {

		log.info("Update custom pricing request received. id={} and request dto= {}", id, request);

		try {
			Optional<SystemRateCardCustomPricing> pricing = customerPricingRepo.findById(id);
			if (pricing.isEmpty()) {
				return new ApiResponse<>(false, "Custom pricing not found", null);
			}
			SystemRateCardCustomPricing SystemRateCardCustomPricing = pricing.get();

			SystemRateCardCustomPricing.setStakeHolderName(request.getStakeHolderName());
			SystemRateCardCustomPricing.setDiscountPercentage(request.getDiscountPercentage());
			SystemRateCardCustomPricing.setUpdatedOn(LocalDateTime.now());
			SystemRateCardCustomPricing.setUpdatedOn(AppUtils.getCurrentDateTime());

			customerPricingRepo.save(SystemRateCardCustomPricing);

			log.info("Custom pricing updated successfully. id={}", id);

			return new ApiResponse<>(true, "Custom pricing updated successfully", SystemRateCardCustomPricing);

		} catch (IllegalArgumentException e) {
			log.warn("Update failed for custom pricing. id={}, reason={}", id, e.getMessage());
			// throw e;
			
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		} catch (Exception e) {
			log.error("Unexpected error while updating custom pricing. id={}", id, e);
			// throw e;
	
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		}
	}

	// DEACTIVATE
	@Override
	public ApiResponse<?> deactivateCustomPricing(Long id) {

		log.info("Deactivate custom pricing request received. id={}", id);

		try {
			Optional<SystemRateCardCustomPricing> pricing = customerPricingRepo.findById(id);
			if (pricing.isEmpty()) {
				return new ApiResponse<>(false, "Custom pricing not found", null);
			}

			pricing.get().setIsActive(false);
			pricing.get().setUpdatedOn(LocalDateTime.now());

			customerPricingRepo.save(pricing.get());

			log.info("Custom pricing deactivated successfully. id={}", id);

			return new ApiResponse<>(true, "Custom pricing deactivated successfully", pricing.get());

		} catch (IllegalArgumentException e) {
			log.warn("Deactivation failed. id={}, reason={}", id, e.getMessage());
			// throw e;
			
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		} catch (Exception e) {
			log.error("Unexpected exception", e);

			log.error("Unexpected error while deactivating custom pricing. id={}", id, e);
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);

		}
	}

	// ACTIVATE
	@Override
	public ApiResponse<?> activateCustomPricing(Long id) {

		log.info("Activate custom pricing request received. id={}", id);

		try {
			Optional<SystemRateCardCustomPricing> pricing = customerPricingRepo.findById(id);
			if (pricing.isEmpty()) {
				return new ApiResponse<>(false, "Custom pricing not found", null);
			}

			if (pricing.get().getIsActive()) {
				log.info("Custom pricing already active. id={}", id);
				return new ApiResponse<>(true, "Custom pricing is already active", pricing.get());
			}

			pricing.get().setIsActive(true);
			pricing.get().setUpdatedOn(LocalDateTime.now());

			customerPricingRepo.save(pricing.get());

			log.info("Custom pricing activated successfully. id={}", id);

			return new ApiResponse<>(true, "Custom pricing activated successfully", pricing.get());

		} catch (IllegalArgumentException e) {
		
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		} catch (Exception e) {
			
			return new ApiResponse<>(false, "something went wrong. please try after sometime", null);
		}
	}

	@Override
	public ApiResponse<?> getCustomRateCard() {
		return new ApiResponse<>(true, "Success", customerPricingRepo.findAll());
	}

	@Override
	public ApiResponse<Page<ServiceProviderCustomPricingResponse>> search(
			ServiceProviderCustomPricingFilterRequest request) {

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

		Page<SystemRateCardCustomPricing> page = customerPricingRepo.searchCustomPricing(request.getOrganizationId(),
				request.getServiceName(), pageable);

		Page<ServiceProviderCustomPricingResponse> response = page.map(this::mapToResponse);

		return new ApiResponse<>(true, "Custom pricing fetched successfully", response);
	}

	private ServiceProviderCustomPricingResponse mapToResponse(SystemRateCardCustomPricing scp) {

		BigDecimal basePrice = scp.getSystemService().getPricePerTransaction();

		BigDecimal discount = scp.getDiscountPercentage();

		BigDecimal priceAfterDiscount = basePrice
				.subtract(basePrice.multiply(discount).divide(BigDecimal.valueOf(100)));

		return new ServiceProviderCustomPricingResponse(scp.getId(), scp.getStakeHolderId(), scp.getStakeHolderName(),
				scp.getSystemService().getId(), scp.getSystemService().getServiceName(),
				scp.getSystemService().getServiceCode(), basePrice, discount, priceAfterDiscount);
	}
}
