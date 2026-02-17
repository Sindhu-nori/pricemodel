package com.dtt.service.impl;

import java.math.BigDecimal;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtt.model.ClientProjects;
import com.dtt.model.SystemRateCard;
import com.dtt.model.SystemRateCardVolumes;
import com.dtt.repo.ClientProjectsRepo;
import com.dtt.repo.SystemRateCardCustomPricingRepo;
import com.dtt.repo.SystemRateCardRepo;
import com.dtt.repo.SystemRateCardVolumesRepo;
import com.dtt.requestdto.CreateRateCardDto;
import com.dtt.requestdto.CreateSystemServiceDto;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.RateCardIface;
import com.dtt.utils.AppUtils;

import jakarta.transaction.Transactional;

@Service
public class RateCardImpl implements RateCardIface {

	private static final Logger log = LoggerFactory.getLogger(RateCardImpl.class);

	@Autowired
	SystemRateCardRepo servicesRepo;

	@Autowired
	SystemRateCardVolumesRepo rateCardRepo;

	@Autowired
	SystemRateCardCustomPricingRepo customerPricingRepo;

	@Autowired
	ClientProjectsRepo clientProjectsRepo;

	// CREATE RATE CARD
	@Override
	public ApiResponse<?> createSystemServiceWithRateCards(CreateSystemServiceDto dto) {

		log.info("Create rate card request received. serviceCode={}", dto);

		try {
			
			if(dto.getProject() == null || dto.getProject().trim().isEmpty()) {
				return new ApiResponse<>(false, "Project Id can't be null or empty ::" + dto.getProject(), null);
			}
			
			Optional<ClientProjects> clientProjects = clientProjectsRepo.findByClientProjectId(dto.getProject()); 
			
			if(clientProjects.isEmpty()) {
				return new ApiResponse<>(false, "Project id not found ::" + dto.getProject(), null);
			}
			
			// 1️⃣ Check duplicate serviceCode
			Optional<SystemRateCard> existing = servicesRepo.findByServiceCode(dto.getServiceCode());

			if (existing.isPresent()) {
				log.warn("Service already exists. serviceCode={}", dto.getServiceCode());
				return new ApiResponse<>(false, "Service with code already exists: " + dto.getServiceCode(), null);
			}

			ApiResponse<?> vatValidation = validateVatRules(dto);
			if (vatValidation != null) {
				return vatValidation;
			}

			// 2️⃣ Validate volume slabs
			List<CreateRateCardDto> rateCards = dto.getRateCards();

			for (CreateRateCardDto r : rateCards) {
				if (r.getMinVolume() > r.getMaxVolume()) {
					log.warn("Invalid volume range. min={}, max={}", r.getMinVolume(), r.getMaxVolume());
					return new ApiResponse<>(false, "Rate card minVolume cannot be greater than maxVolume", null);
				}
			}

			rateCards.sort(Comparator.comparingInt(CreateRateCardDto::getMinVolume));
			for (int i = 1; i < rateCards.size(); i++) {
				if (rateCards.get(i).getMinVolume() <= rateCards.get(i - 1).getMaxVolume()) {

					log.warn("Overlapping volume slabs detected. serviceCode={}", dto.getServiceCode());

					return new ApiResponse<>(false, "Rate card ranges overlap", null);
				}
			}

			// 3️⃣ Create base service
			SystemRateCard entity = new SystemRateCard();

			entity.setProject(clientProjects.get());

			entity.setServiceCode(dto.getServiceCode());
			entity.setServiceName(dto.getServiceName());
			entity.setServiceType(dto.getServiceType());
			entity.setPricePerTransaction(dto.getPricePerTransaction());
			entity.setPlatformFeePercent(dto.getPlatformFeePercent());
			entity.setGovernmentVatPercent(dto.getGovernmentVatPercent());
			entity.setVatIncludedInPricing(dto.getVatIncludedInPricing());

			entity.setRateEffectiveFrom(AppUtils.parseDateTime(dto.getRateEffectiveFrom()));
			entity.setRateEffectiveUpto(
					dto.getRateEffectiveUpto() == null ? null : AppUtils.parseDateTime(dto.getRateEffectiveUpto()));

			entity.setIsActive(dto.getIsActive());

			// LocalDateTime now = LocalDateTime.now();
			entity.setCreatedOn(AppUtils.getCurrentDateTime());
			entity.setUpdatedOn(AppUtils.getCurrentDateTime());

			SystemRateCard savedService = servicesRepo.save(entity);

			log.info("System rate card created. serviceId={}, serviceCode={}", savedService.getId(),
					savedService.getServiceCode());

			// 4️⃣ Create volume slabs
			List<SystemRateCardVolumes> toSave = new ArrayList<>();

			for (CreateRateCardDto rcDto : rateCards) {
				SystemRateCardVolumes rc = new SystemRateCardVolumes();
				rc.setSystemService(savedService);
				rc.setMinVolume(rcDto.getMinVolume());
				rc.setMaxVolume(rcDto.getMaxVolume());
				rc.setPricePerTransaction(rcDto.getPricePerTransaction());
//				rc.setRateEffectiveFrom(AppUtils.parseDateTime(rcDto.getRateEffectiveFrom()));
//				rc.setRateEffectiveUpto(rcDto.getRateEffectiveUpto() == null ? null
//						: AppUtils.parseDateTime(rcDto.getRateEffectiveUpto()));
				rc.setCreatedOn(AppUtils.getCurrentDateTime());
				rc.setUpdatedOn(AppUtils.getCurrentDateTime());
				rc.setStatus("ACTIVE");

				toSave.add(rc);
			}

			rateCardRepo.saveAll(toSave);

			log.info("Volume slabs created successfully. serviceId={}, slabCount={}", savedService.getId(),
					toSave.size());

			return new ApiResponse<>(true, "Service and rate cards created", savedService);

		} catch (DateTimeParseException e) {
			log.error("Invalid date format in rate card creation. serviceCode={}", dto.getServiceCode(), e);
			return new ApiResponse<>(false, "Invalid date format. Use yyyy-MM-dd HH:mm:ss", null);
		} catch (Exception e) {
			log.error("Unexpected error while creating rate card. serviceCode={}", dto.getServiceCode(), e);
			return new ApiResponse<>(false, "Error creating service", null);
		}
	}

	// UPDATE RATE CARD
	@Transactional
	@Override
	public ApiResponse<?> updateSystemServiceRateCards(Long serviceId, CreateSystemServiceDto dto) {

		log.info("Update rate card request received. serviceId={}, dto={}", serviceId, dto);
		
		if(dto.getProject() == null || dto.getProject().trim().isEmpty()) {
			return new ApiResponse<>(false, "Project Id can't be null or empty ::" + dto.getProject(), null);
		}
		
		Optional<ClientProjects> clientProjects = clientProjectsRepo.findByClientProjectId(dto.getProject()); 
		
		if(clientProjects.isEmpty()) {
			return new ApiResponse<>(false, "Project id not found ::" + dto.getProject(), null);
		}

		// 1️⃣ Fetch service
		Optional<SystemRateCard> SystemRateCard = servicesRepo.findById(serviceId);

		if (SystemRateCard.isEmpty()) {
			return new ApiResponse<>(false, "Service rate card not found " + dto.getServiceCode(), null);
		}

		// 2️⃣ Validate serviceCode uniqueness
		Optional<SystemRateCard> existing = servicesRepo.findByServiceCode(dto.getServiceCode());
		if (existing.isPresent() && existing.get().getId() != serviceId) {
			// existing.isPresent() && existing.get().getId() != serviceId
			return new ApiResponse<>(false, "Service code already exists: " + dto.getServiceCode(), null);
		}

		// 3️⃣ VAT validation
		ApiResponse<?> vatValidation = validateVatRules(dto);
		if (vatValidation != null) {
			return vatValidation;
		}

		// 4️⃣ Validate slabs
		List<CreateRateCardDto> rateCards = dto.getRateCards();

		rateCards.sort(Comparator.comparingInt(CreateRateCardDto::getMinVolume));
		for (int i = 0; i < rateCards.size(); i++) {
			CreateRateCardDto curr = rateCards.get(i);

			if (curr.getMinVolume() > curr.getMaxVolume()) {
				return new ApiResponse<>(false, "minVolume cannot be greater than maxVolume", null);
			}

			if (i > 0 && curr.getMinVolume() <= rateCards.get(i - 1).getMaxVolume()) {
				return new ApiResponse<>(false, "Rate card ranges overlap", null);
			}
		}

		// 5️⃣ Update base service
		SystemRateCard service = SystemRateCard.get();
		service.setProject(clientProjects.get());
		service.setServiceCode(dto.getServiceCode());
		service.setServiceType(dto.getServiceType());
		service.setServiceName(dto.getServiceName());
		service.setPricePerTransaction(dto.getPricePerTransaction());
		service.setPlatformFeePercent(dto.getPlatformFeePercent());
		service.setGovernmentVatPercent(dto.getGovernmentVatPercent());
		service.setVatIncludedInPricing(dto.getVatIncludedInPricing());
		service.setIsActive(dto.getIsActive());

		service.setRateEffectiveFrom(AppUtils.parseDateTime(dto.getRateEffectiveFrom()));
		service.setRateEffectiveUpto(
				dto.getRateEffectiveUpto() == null ? null : AppUtils.parseDateTime(dto.getRateEffectiveUpto()));

		service.setUpdatedOn(AppUtils.getCurrentDateTime());

		servicesRepo.save(service);

		// 6️⃣ Close old slabs (IMPORTANT)
		//int closed = rateCardRepo.deleteBySystemServiceId(serviceId);
		int closed = rateCardRepo.updateStatusBySystemServiceId(serviceId, "INACTIVE");
		log.info("Closed {} old slabs for serviceId={}", closed, serviceId);

		// 7️⃣ Insert new slabs
		List<SystemRateCardVolumes> newVolumes = new ArrayList<>();

		for (CreateRateCardDto rcDto : rateCards) {
			SystemRateCardVolumes rc = new SystemRateCardVolumes();
			rc.setSystemService(service);
			rc.setMinVolume(rcDto.getMinVolume());
			rc.setMaxVolume(rcDto.getMaxVolume());
			rc.setPricePerTransaction(rcDto.getPricePerTransaction());
//			rc.setRateEffectiveFrom(AppUtils.parseDateTime(rcDto.getRateEffectiveFrom()));
//			rc.setRateEffectiveUpto(
//					rcDto.getRateEffectiveUpto() == null ? null : AppUtils.parseDateTime(rcDto.getRateEffectiveUpto()));
			rc.setCreatedOn(AppUtils.getCurrentDateTime());
			rc.setUpdatedOn(AppUtils.getCurrentDateTime());

			newVolumes.add(rc);
		}

		rateCardRepo.saveAll(newVolumes);

		log.info("Rate card updated successfully. serviceId={}, newSlabs={}", serviceId, newVolumes.size());

		return new ApiResponse<>(true, "Rate card updated successfully", newVolumes);
	}
//	@Transactional
//	@Override
//	public ApiResponse<?> updateSystemServiceRateCards(Long serviceId, CreateSystemServiceDto dto) {
//
//		log.info("Update rate card request received. serviceId={} and request dto={}", serviceId,dto);
//
//		try {
//			// 1️⃣ Fetch service
//			SystemRateCard service = servicesRepo.findById(serviceId).orElseThrow(() -> {
//				log.warn("Service rate card not found. serviceId={}", serviceId);
//				return new IllegalArgumentException("Service rate card not found");
//			});
//
//			// 2️⃣ Validate serviceCode uniqueness
//			Optional<SystemRateCard> existing = servicesRepo.findByServiceCode(dto.getServiceCode());
//
//			if (existing.isPresent() && existing.get().getId() != serviceId) {
//				log.warn("Duplicate serviceCode during update. serviceCode={}", dto.getServiceCode());
//				return new ApiResponse<>(false, "Service code already exists: " + dto.getServiceCode(), null);
//			}
//
//			ApiResponse<?> vatValidation = validateVatRules(dto);
//			if (vatValidation != null) {
//				return vatValidation;
//			}
//
//			// 3️⃣ Validate volume slabs
//			List<CreateRateCardDto> rateCards = dto.getRateCards();
//
//			for (CreateRateCardDto r : rateCards) {
//				if (r.getMinVolume() > r.getMaxVolume()) {
//					log.warn("Invalid volume range during update. serviceId={}", serviceId);
//					return new ApiResponse<>(false, "minVolume cannot be greater than maxVolume", null);
//				}
//			}
//
//			rateCards.sort(Comparator.comparingInt(CreateRateCardDto::getMinVolume));
//			for (int i = 1; i < rateCards.size(); i++) {
//				if (rateCards.get(i).getMinVolume() <= rateCards.get(i - 1).getMaxVolume()) {
//
//					log.warn("Overlapping slabs during update. serviceId={}", serviceId);
//					return new ApiResponse<>(false, "Rate card ranges overlap", null);
//				}
//			}
//
//			// 4️⃣ Update base service
//			service.setProjectId(dto.getProjectId());
//			//service.setId(serviceId);
//			service.setServiceCode(dto.getServiceCode());
//			service.setServiceType(dto.getServiceType());
//			service.setServiceName(dto.getServiceName());
//			service.setPricePerTransaction(dto.getPricePerTransaction());
//			service.setPlatformFeePercent(dto.getPlatformFeePercent());
//			service.setGovernmentVatPercent(dto.getGovernmentVatPercent());
//			service.setVatIncludedInPricing(dto.getVatIncludedInPricing());
//			service.setIsActive(dto.getIsActive());
//			service.setUpdatedOn(AppUtils.getCurrentDateTime());
//
//			servicesRepo.save(service);
//
//			// 5️⃣ Close old slabs
//			rateCardRepo.deactivateBySystemServiceId(serviceId);
//
//			// 6️⃣ Insert new slabs
//			//LocalDateTime now = LocalDateTime.now();
//			List<SystemRateCardVolumes> newVolumes = new ArrayList<>();
//
//			for (CreateRateCardDto rcDto : rateCards) {
//				SystemRateCardVolumes rc = new SystemRateCardVolumes();
//				rc.setSystemService(service);
//				rc.setMinVolume(rcDto.getMinVolume());
//				rc.setMaxVolume(rcDto.getMaxVolume());
//				rc.setPricePerTransaction(rcDto.getPricePerTransaction());
//				rc.setRateEffectiveFrom(AppUtils.parseDateTime(rcDto.getRateEffectiveFrom()));
//				rc.setRateEffectiveUpto(rcDto.getRateEffectiveUpto() == null ? null
//						: AppUtils.parseDateTime(rcDto.getRateEffectiveUpto()));
//				rc.setCreatedOn(AppUtils.getCurrentDateTime());
//				rc.setUpdatedOn(AppUtils.getCurrentDateTime());
//
//				newVolumes.add(rc);
//			}
//
//			rateCardRepo.saveAll(newVolumes);
//
//			log.info("Rate card updated successfully. serviceId={}, slabCount={}", serviceId, newVolumes.size());
//
//			return new ApiResponse<>(true, "Rate card updated successfully", newVolumes);
//
//		} catch (Exception e) {
//			log.error("Unexpected error while updating rate card. serviceId={}", serviceId, e);
//			e.printStackTrace();
//			return new ApiResponse<>(false, "Failed to update rate card", null);
//		}
//	}

	private ApiResponse<?> validateVatRules(CreateSystemServiceDto dto) {

		if (dto.getVatIncludedInPricing()) {
			if (dto.getGovernmentVatPercent() != null && dto.getGovernmentVatPercent().compareTo(BigDecimal.ZERO) > 0) {
				return new ApiResponse<>(false, "When VAT is included in pricing, governmentVatPercent must be 0",
						null);
			}
		} else { // VAT NOT included
			if (dto.getGovernmentVatPercent() == null
					|| dto.getGovernmentVatPercent().compareTo(BigDecimal.ZERO) <= 0) {
				return new ApiResponse<>(false, "When VAT is not included, governmentVatPercent must be greater than 0",
						null);
			}
		}
		return null; // valid
	}

	@Override
	public ApiResponse<List<ClientProjects>> getAllProjects() {
	    try {
	        List<ClientProjects> clientProjects = clientProjectsRepo.findAll();

	        if (!clientProjects.isEmpty()) {
	            return new ApiResponse<>(true, "success", clientProjects);
	        } else {
	            return new ApiResponse<>(false, "No record found", null);
	        }

	    } catch (Exception e) {
	        return new ApiResponse<>(false,
	                "Something went wrong. Please try after sometime",
	                null);
	    }
	}


	@Override
	public ApiResponse<List<ClientProjects>> createProjectId(List<ClientProjects> clientProjects) {
		try {
			if (clientProjects == null || clientProjects.isEmpty()) {
				return new ApiResponse<>(false, "Project list is empty", null);
			}
			List<ClientProjects> savedProjects = new ArrayList<>();
			for (ClientProjects project : clientProjects) {
				// Basic validation
				if (project.getClientProjectId() == null || project.getClientProjectId().isBlank()) {
					return new ApiResponse<>(false, "Client Project ID cannot be null or empty", null);
				}

				// Duplicate check
				Optional<ClientProjects> existing = clientProjectsRepo
						.findByClientProjectId(project.getClientProjectId());

				if (existing.isPresent()) {
					return new ApiResponse<>(false, "Project ID already exists: " + project.getClientProjectId(), null);
				}

				savedProjects.add(project);
			}

			clientProjectsRepo.saveAll(savedProjects);
			return new ApiResponse<>(true, "Success", savedProjects);
		} catch (Exception e) {
			return new ApiResponse<>(false, "Something went wrong. Please try after sometime", null);
		}
	}

}
