package com.dtt.controller;

import com.dtt.requestdto.WalletTransactionFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.WalletCustomerIface;

@RestController
@RequestMapping("/api")
public class WalletCustomerController {
	
	@Autowired
	WalletCustomerIface customerIface;
	
	@GetMapping("/get/walletlist")
	public ApiResponse<?> getWalletList() {
		return customerIface.getWalletList();
	}
	
	@GetMapping("/get/wallettransaction")
	public ApiResponse<?> getWalletTransactionHistory() {
		return customerIface.getWalletTransactionHistory();
	}
	
	@GetMapping("/get/wallettransaction/{ouid}")
	public ApiResponse<?> getWalletTransactionHistoryByOuid(@PathVariable("ouid") String ouid) {
		return customerIface.getWalletTransactionHistoryByOuid(ouid);
	}

	@PostMapping("/wallet/transaction/{ouid}")
	public ApiResponse<?> postAuditLogsByFilters(
			@PathVariable("ouid") String ouid,
			@Validated @RequestBody WalletTransactionFilterDTO pageDTO) {

		return customerIface.getWalletTransactionHistoryByOuidWithFilters(ouid,pageDTO);

	}
}
