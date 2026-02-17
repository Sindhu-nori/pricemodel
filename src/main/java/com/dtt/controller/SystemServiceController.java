package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.SystemServiceIface;

@RestController
@RequestMapping("/api/get")
public class SystemServiceController {

	@Autowired
	SystemServiceIface serviceIface;

	@GetMapping("/status")
	public String getStatus() {
		return "Paynova Price model service running";
	}

	@GetMapping("/services")
	public ApiResponse<?> getSystemService(@RequestParam("projectId") String projectId) {
		return serviceIface.getSystemService(projectId);
	}
	
	@GetMapping("/rate-card")
	public ApiResponse<?> getSystemServiceRateCard(@RequestParam("serviceId") Long serviceId,
			@RequestParam(value = "stakeholderId", required = false) String stakeholderId) {
		return serviceIface.getSystemServiceRateCard(serviceId, stakeholderId);
	}
	
	@GetMapping("/rate-card/by-serviceId")
	public ApiResponse<?> getRateCardByServiceId(@RequestParam("serviceId") Long serviceId) {
		return serviceIface.getRateCardByServiceId(serviceId);
	}

	@GetMapping("/rate-card/by-stakeholderId")
	public ApiResponse<?> getRateCardByStakeHolderID(@RequestParam("serviceId") Long serviceId,
			@RequestParam(value = "stakeholderId", required = false) String stakeholderId) {
		return serviceIface.getRateCardByStakeHolderID(serviceId, stakeholderId);
	}
	
	public ApiResponse<?> getRateCardByCustomerCodeAndOuid(@RequestParam("serviceCode") String serviceCode,@RequestParam("ouid") String ouid){
		return serviceIface.getRateCardByCustomerCodeAndOuid(serviceCode, ouid);
	}
	
	@GetMapping("/rate-card/by-ouid/{ouid}")
	public ApiResponse<?> getRateCardByOUID(@PathVariable("ouid") String stakeholderId) {
		return serviceIface.getRateCardByOUID(stakeholderId);
	}

}
