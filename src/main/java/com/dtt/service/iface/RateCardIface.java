package com.dtt.service.iface;

import java.util.List;

import com.dtt.model.ClientProjects;
import com.dtt.requestdto.CreateSystemServiceDto;
import com.dtt.responsedto.ApiResponse;

public interface RateCardIface {

	ApiResponse<?> createSystemServiceWithRateCards(CreateSystemServiceDto dto);

	ApiResponse<?> updateSystemServiceRateCards(Long serviceId, CreateSystemServiceDto dto);
	
	ApiResponse<?> getAllProjects();
	
	ApiResponse<?> createProjectId(List<ClientProjects> clientProjects);

}
