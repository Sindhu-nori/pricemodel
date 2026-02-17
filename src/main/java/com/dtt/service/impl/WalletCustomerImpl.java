package com.dtt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtt.model.CustomerEnrollment;
import com.dtt.model.PaymentHistory;
import com.dtt.repo.CustomerEnrollmentRepository;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.WalletCustomerIface;

@Service
public class WalletCustomerImpl implements WalletCustomerIface{

	@Autowired
	CustomerEnrollmentRepository customerEnrollmentRepository;
	
	@Autowired
	PaymentHistoryRepo historyRepo;
	
	@Override
	public ApiResponse<?> getWalletList() {
		try {
			List<CustomerEnrollment> customerEnrollment = customerEnrollmentRepository.findAll();
			return new ApiResponse<>(true,"Success",customerEnrollment);
		} catch (Exception e) {
		log.error("Unexpected exception", e);
			return new ApiResponse<>(false, "Something went wrong. please try after sometime", null);
		}
	}

	@Override
	public ApiResponse<?> getWalletTransactionHistory() {
		try {
			List<PaymentHistory> list =historyRepo.findPaymentHistorySuccessAndFailed();
			return new ApiResponse<>(true,"Success",list);
		} catch (Exception e) {
         log.error("Unexpected exception", e);

			return new ApiResponse<>(false, "Something went wrong. please try after sometime", null);
		}
	}

	@Override
	public ApiResponse<?> getWalletTransactionHistoryByOuid(String ouid) {
		try {
			List<PaymentHistory> list = historyRepo.findSuccessAndFailedByOrganizationId(ouid);
			if(list.isEmpty()) {
				return new ApiResponse<>(false, "no record found", null);
			}
			return new ApiResponse<>(true, "Success", list);
		} catch (Exception e) {
			log.error("Unexpected exception", e);
			return new ApiResponse<>(false, "Something went wrong. please try after sometime", null);
		}
	}

}
