package com.dtt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.model.ClientProjects;
import com.dtt.requestdto.CreateSystemServiceDto;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.RateCardIface;

@RestController
@RequestMapping("/api")
public class RateCardController {

	@Autowired
	RateCardIface rateCardIface;

	@PostMapping("/rate-cards/add")
	public ApiResponse<?> createService(@RequestBody CreateSystemServiceDto dto) {
		return rateCardIface.createSystemServiceWithRateCards(dto);
		// return response;
	}

	@PostMapping("/rate-cards/{serviceId}")
	public ApiResponse<?> updateRateCard(@PathVariable("serviceId") Long serviceId,
			@RequestBody CreateSystemServiceDto dto) {

		return rateCardIface.updateSystemServiceRateCards(serviceId, dto);
	}

	@GetMapping("/get/all-project")
	public ApiResponse<?> getAllProjects() {
		return rateCardIface.getAllProjects();
	}
	
	@PostMapping("/create/project-id")
	public ApiResponse<?> createProjectId(@RequestBody List<ClientProjects> clientProjects) {
		return rateCardIface.createProjectId(clientProjects);
	}

}
