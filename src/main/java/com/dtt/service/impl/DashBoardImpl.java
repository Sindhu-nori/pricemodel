package com.dtt.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtt.model.SystemRateCard;
import com.dtt.repo.SystemRateCardCustomPricingRepo;
import com.dtt.repo.SystemRateCardRepo;
import com.dtt.repo.SystemRateCardVolumesRepo;
import com.dtt.responsedto.ApiResponse;
import com.dtt.responsedto.CustomPricingDto;
import com.dtt.responsedto.RateCardDashboardDto;
import com.dtt.responsedto.ServiceDto;
import com.dtt.responsedto.VolumePricingDto;
import com.dtt.service.iface.DashBoardIface;

@Service
public class DashBoardImpl implements DashBoardIface {

	private static final Logger log = LoggerFactory.getLogger(DashBoardImpl.class);

	@Autowired
	SystemRateCardRepo servicesRepo;

	@Autowired
	SystemRateCardVolumesRepo rateCardRepo;

	@Autowired
	SystemRateCardCustomPricingRepo customerPricingRepo;

	@Override
	public ApiResponse<?> getDashboardRateCards() {

		log.info("Dashboard rate card fetch started");

		try {
			// LocalDateTime now = LocalDateTime.now();
			// List<SystemRateCard> services = servicesRepo.findActiveRateCards();
			List<SystemRateCard> services = servicesRepo.findAll();

			if (services == null || services.isEmpty()) {
				log.info("No active system rate cards found");
				return new ApiResponse<>(true, "No rate card data available", List.of());
			}

			List<RateCardDashboardDto> dashboardList = services.stream().map(service -> {

				log.info("Processing dashboard data for serviceId={}, serviceCode={}", service.getId(),
						service.getServiceCode());

				ServiceDto serviceDto = new ServiceDto(service.getId(), service.getProject().getClientProjectId(),
						service.getServiceCode(), service.getServiceName(), service.getServiceType(),
						service.getPricePerTransaction(), service.getPlatformFeePercent(),
						service.getGovernmentVatPercent(), service.getVatIncludedInPricing(),
						service.getRateEffectiveFrom(), service.getRateEffectiveUpto(), service.getIsActive());

				List<VolumePricingDto> volumePricing = rateCardRepo.findActiveVolumes(service.getId()).stream()
						.map(v -> new VolumePricingDto(v.getMinVolume(), v.getMaxVolume(), v.getPricePerTransaction()))
						.toList();

				List<CustomPricingDto> customPricing = customerPricingRepo.findActiveCustomPricing(service.getId())
						.stream().map(c -> new CustomPricingDto(c.getStakeHolderType(), c.getStakeHolderName(),
								c.getStakeHolderId(), c.getDiscountPercentage(), c.getIsActive()))
						.toList();

				return new RateCardDashboardDto(serviceDto, volumePricing, customPricing);

			}).toList();

			log.info("Dashboard rate card fetch completed successfully. totalServices={}", dashboardList.size());

			return new ApiResponse<>(true, "Rate card dashboard data", dashboardList);

		} catch (Exception e) {
			
			log.info("Error while fetching dashboard rate cards", e);
			return new ApiResponse<>(true, "Something went wrong. please try after sometime", null);
		}
	}
}
