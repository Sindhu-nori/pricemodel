package com.dtt.service.iface;

import com.dtt.requestdto.WalletTransactionFilterDTO;
import com.dtt.responsedto.ApiResponse;

public interface WalletCustomerIface {

	ApiResponse<?> getWalletList();

	ApiResponse<?> getWalletTransactionHistory();

	ApiResponse<?> getWalletTransactionHistoryByOuid(String ouid);

	ApiResponse<?> getWalletTransactionHistoryByOuidWithFilters(String ouid, WalletTransactionFilterDTO walletTransactionFilterDTO);



}
