package com.dtt.service.iface;

import com.dtt.responsedto.ApiResponse;



public interface SystemServiceIface {
	
	//public ResponseEntity<ApiResponse<?>> addSystemService(SystemServiceRateCardRequestDto dto);

	ApiResponse<?> getSystemService(String projectId);

	//ApiResponse<?> getPricingResponse();

	ApiResponse<?> getSystemServiceRateCard(Long serviceId,String stakeholderId);

	ApiResponse<?> getRateCardByServiceId(Long serviceId);

	ApiResponse<?> getRateCardByStakeHolderID(Long serviceId,String stakeholderId);

	ApiResponse<?> getRateCardByCustomerCodeAndOuid(String serviceCode, String ouid);

	ApiResponse<?> getRateCardByOUID(String ouid);
	
	 
	

}
