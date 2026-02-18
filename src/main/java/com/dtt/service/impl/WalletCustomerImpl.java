package com.dtt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dtt.requestdto.WalletTransactionFilterDTO;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.dtt.model.CustomerEnrollment;
import com.dtt.model.PaymentHistory;
import com.dtt.repo.CustomerEnrollmentRepository;
import com.dtt.repo.PaymentHistoryRepo;
import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.WalletCustomerIface;

import static java.lang.annotation.RetentionPolicy.CLASS;


@Service
public class WalletCustomerImpl implements WalletCustomerIface{

	@Autowired
	CustomerEnrollmentRepository customerEnrollmentRepository;
	
	@Autowired
	PaymentHistoryRepo historyRepo;


	private static final Logger log = LoggerFactory.getLogger(WalletCustomerImpl.class);
	@Override
	public ApiResponse<?> getWalletList() {
		try {
			List<CustomerEnrollment> customerEnrollment = customerEnrollmentRepository.findAll();
			return new ApiResponse<>(true,"Success",customerEnrollment);
		} catch (Exception e) {
			log.info(CLASS + " Sending PayNova request to {}", e.getMessage());
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


	@Override
	public ApiResponse<?> getWalletTransactionHistoryByOuidWithFilters(String ouid, WalletTransactionFilterDTO walletTransactionFilterDTO) {
		try{
			Integer perPage = walletTransactionFilterDTO.getPerPage();
			int _perPage = (perPage == null) ? 10 : perPage;
			Integer reqPage = walletTransactionFilterDTO.getPage();
			int page = (reqPage == null || reqPage < 1) ? 0 : reqPage - 1;
			Pageable pageable = PageRequest.of(page, _perPage);
			Page<PaymentHistory> history =	historyRepo.findAll(buildDTO(walletTransactionFilterDTO), pageable);
			Map<String, Object> result = new HashMap<>();
			result.put("data", history.getContent());
			result.put("totalRecords", history.getTotalElements());
			result.put("totalPages", history.getTotalPages());
			result.put("currentPage", page + 1);
			result.put("perPage", _perPage);
			return new ApiResponse<>(true,"Wallet Transactions fetched", result);
		}

		catch (Exception e) {
			log.error("Unexpected exception", e);
			return new ApiResponse<>(false, "Something went wrong. please try after sometime", null);
		}
	}

	private Specification<PaymentHistory> buildDTO(WalletTransactionFilterDTO dto) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(root.get("status").in("SUCCESS", "FAILED"));
			if (dto.getStatus() != null && !dto.getStatus().equalsIgnoreCase("ALL")) {
				if (!dto.getStatus().equalsIgnoreCase("INITIATED")) {
					predicates.add(cb.equal(root.get("status"), dto.getStatus()));
				}
			}
			if (dto.getTransactionType() != null) {
				predicates.add(cb.equal(root.get("transactionType"), dto.getTransactionType()));
			}
			if (dto.getTransactionId() != null && !dto.getTransactionId().isBlank()) {
				predicates.add(cb.like(cb.lower(root.get("transactionId")), "%" + dto.getTransactionId().toLowerCase() + "%"));
			}
			if (dto.getFromDate() != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("createdOn"), dto.getFromDate().atStartOfDay()));
			}
			if (dto.getToDate() != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("createdOn"), dto.getToDate().atTime(23, 59, 59)));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
