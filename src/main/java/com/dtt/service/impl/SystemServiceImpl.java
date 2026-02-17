package com.dtt.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.dtt.model.ClientProjects;
import com.dtt.model.SystemRateCard;
import com.dtt.model.SystemRateCardCustomPricing;
import com.dtt.model.SystemRateCardVolumes;
import com.dtt.repo.ClientProjectsRepo;
import com.dtt.repo.SystemRateCardCustomPricingRepo;
import com.dtt.repo.SystemRateCardVolumesRepo;
import com.dtt.repo.SystemRateCardRepo;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.RateCardResponseDto;
import com.dtt.responsedto.SystemServiceResponseDto;
import com.dtt.service.iface.SystemServiceIface;

@Service
public class SystemServiceImpl implements SystemServiceIface {

	private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

	@Autowired
	SystemRateCardRepo servicesRepo;

	@Autowired
	SystemRateCardVolumesRepo rateCardRepo;

	@Autowired
	SystemRateCardCustomPricingRepo customerPricingRepo;
	
	@Autowired
	ClientProjectsRepo clientProjectsRepo;

	// ----------------------------------------------------
	// STATUS / HEALTH
	// ----------------------------------------------------
//	@Override
//	public ApiResponse<?> getPricingResponse() {
//		return new ApiResponse<>(true, "Service running", null);
//	}

	// GET SYSTEM SERVICES
	@Override
	public ApiResponse<?> getSystemService(String projectId) {

		log.info("Fetching system services. projectId={}", projectId);

		try {
			if (projectId == null || projectId.trim().isEmpty()) {
				log.info("Invalid projectId received");
				return new ApiResponse<>(false, "Project Id can't be null or empty", null);
			}
			
			// 1️⃣ Fetch ClientProjects entity using business ID
	        ClientProjects project = clientProjectsRepo.findByClientProjectId(projectId).orElse(null);

	        if (project == null) {
	            log.info("Project not found for projectId={}", projectId);
	            return new ApiResponse<>(false, "Project not found: " + projectId, null);
	        }

			List<SystemRateCard> services = servicesRepo.findByProjectAndIsActiveTrue(project);

			if (services.isEmpty()) {
				log.info("No services found for projectId={}", projectId);
				return new ApiResponse<>(false, "System Service not found for project: " + projectId, null);
			}

			log.info("System services fetched successfully. projectId={}, count={}", projectId, services.size());

			return new ApiResponse<>(true, "OK", services);

		} catch (Exception e) {
			
			log.info("Error while fetching system services. projectId={}", projectId, e);
			return new ApiResponse<>(false, "Something went wrong. Please try after sometime", null);
		}
	}

	// GET RATE CARD (GENERIC OR CUSTOM)
	@Override
	public ApiResponse<?> getSystemServiceRateCard(Long serviceId, String stakeholderId) {

		log.info("Fetching rate card. serviceId={}, stakeholderId={}", serviceId, stakeholderId);

		try {
			if (stakeholderId != null && !stakeholderId.isEmpty()) {
				return getRateCardBasedOnStakeHolderId(serviceId, stakeholderId);
			} else {
				return getGenericRateCard(serviceId);
			}
		} catch (Exception e) {
			log.info("Error while fetching rate card. serviceId={}", serviceId, e);
			return new ApiResponse<>(false, "Something went wrong. Please try after sometime", null);
		}
	}

	// CUSTOM RATE CARD
	public ApiResponse<?> getRateCardBasedOnStakeHolderId(Long serviceId, String stakeholderId) {

		log.info("Fetching custom rate card. serviceId={}, stakeholderId={}", serviceId, stakeholderId);

		try {
			List<SystemRateCardCustomPricing> customRates = customerPricingRepo
					.findActiveBySystemServiceIdAndStakeHolderId(serviceId, stakeholderId);

			if (customRates == null || customRates.isEmpty()) {
				log.info("No custom rate card found. serviceId={}, stakeholderId={}", serviceId, stakeholderId);
				return new ApiResponse<>(false, "No custom rate card found for stakeholder", null);
			}

			List<RateCardResponseDto> dtos = customRates.stream().map(cp -> {

				SystemRateCard s = cp.getSystemService();

				SystemServiceResponseDto ssDto = new SystemServiceResponseDto();
				ssDto.setId(s.getId());
				
				ssDto.setProjectId(s.getProject().getClientProjectId());
				
				ssDto.setServiceType(s.getServiceType());
				ssDto.setPricePerTransaction(s.getPricePerTransaction());
				ssDto.setPlatformFeePercent(s.getPlatformFeePercent());
				ssDto.setGovernmentVatPercent(s.getGovernmentVatPercent());
				ssDto.setVatIncludedInPricing(s.getVatIncludedInPricing());
				ssDto.setServiceName(s.getServiceName());
				ssDto.setServiceCode(s.getServiceCode());

				RateCardResponseDto rc = new RateCardResponseDto();
				rc.setSystemService(ssDto);
				//rc.setMinVolume(null);
				//rc.setMaxVolume(null);
				rc.setDiscountPercentage(cp.getDiscountPercentage());

				return rc;
			}).collect(Collectors.toList());

			log.info("Custom rate card fetched successfully. count={}", dtos.size());
			return new ApiResponse<>(true, "Custom rate card found", dtos);

		} catch (Exception e) {
			
			log.info("Error while fetching custom rate card. serviceId={}, stakeholderId={}", serviceId, stakeholderId,
					e);
			return new ApiResponse<>(false, "Something went wrong. Please try again later", null);
		}
	}

	// GENERIC RATE CARD
	public ApiResponse<?> getGenericRateCard(Long serviceId) {
		log.info("Fetching generic rate card. serviceId={}", serviceId);
		try {
			List<SystemRateCardVolumes> volumes = rateCardRepo.findActiveAndSystemServiceId(serviceId);
			if (volumes != null && !volumes.isEmpty()) {

				List<RateCardResponseDto> dtos = volumes.stream().map(this::mapVolumeToDto)
						.collect(Collectors.toList());

				return new ApiResponse<>(true, "Generic rate card found", dtos);
			}
			Optional<SystemRateCard> baseRateOpt = servicesRepo.findActiveRateCardByServiceId(serviceId);

			if (baseRateOpt.isPresent()) {

				RateCardResponseDto dto = mapBaseRateToDto(baseRateOpt.get());

				return new ApiResponse<>(true, "Generic rate card found", List.of(dto));
			}
			log.info("No generic rate card found. serviceId={}", serviceId);
			return new ApiResponse<>(false, "No generic rate card found for serviceId: " + serviceId, null);

		} catch (Exception e) {
		
			log.info("Error while fetching generic rate card. serviceId={}", serviceId, e);
			return new ApiResponse<>(false, "Something went wrong. Please try again later", null);
		}
	}

	private RateCardResponseDto mapVolumeToDto(SystemRateCardVolumes r) {
		SystemRateCard s = r.getSystemService();
		RateCardResponseDto rc = new RateCardResponseDto();
		rc.setSystemService(mapSystemService(s));
		rc.setMinVolume(r.getMinVolume());
		rc.setMaxVolume(r.getMaxVolume());
		//rc.setDiscountPercentage(r.getPricePerTransaction());
		return rc;
	}

	private RateCardResponseDto mapBaseRateToDto(SystemRateCard s) {
		RateCardResponseDto rc = new RateCardResponseDto();
		rc.setSystemService(mapSystemService(s));
		rc.setMinVolume(null);
		rc.setMaxVolume(null);
		//rc.setDiscountPercentage(s.getPricePerTransaction());
		return rc;
	}

	private SystemServiceResponseDto mapSystemService(SystemRateCard s) {
		SystemServiceResponseDto ss = new SystemServiceResponseDto();
		ss.setId(s.getId());
		
		ss.setProjectId(s.getProject().getClientProjectId());
		
		ss.setServiceType(s.getServiceType());
		ss.setPricePerTransaction(s.getPricePerTransaction());
		ss.setPlatformFeePercent(s.getPlatformFeePercent());
		ss.setGovernmentVatPercent(s.getGovernmentVatPercent());
		ss.setVatIncludedInPricing(s.getVatIncludedInPricing());
		ss.setServiceName(s.getServiceName());
		ss.setServiceCode(s.getServiceCode());
		return ss;
	}

	// DELEGATES
	@Override
	public ApiResponse<?> getRateCardByServiceId(Long serviceId) {
		return getGenericRateCard(serviceId);
	}

	@Override
	public ApiResponse<?> getRateCardByStakeHolderID(Long serviceId, String stakeholderId) {
		return getRateCardBasedOnStakeHolderId(serviceId, stakeholderId);
	}

	@Override
	public ApiResponse<?> getRateCardByCustomerCodeAndOuid(String serviceCode, String ouid) {

		return null;
	}

	@Override
	public ApiResponse<?> getRateCardByOUID(String ouid) {
		try {
			List<SystemRateCardCustomPricing> cardCustomPricings = customerPricingRepo.findByStakeHolderId(ouid);
			if(cardCustomPricings.isEmpty()) {
				return new ApiResponse<>(false, "no record found", null);
			}
			return new ApiResponse<>(true, "Success", cardCustomPricings);
		} catch (Exception e) {
        log.error("Unexpected exception", e);

			return new ApiResponse<>(false, "Something went wrong. Please try after sometime",null);
		}
	}
}
