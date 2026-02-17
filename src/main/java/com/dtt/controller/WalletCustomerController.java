package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
