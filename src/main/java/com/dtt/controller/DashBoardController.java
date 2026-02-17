package com.dtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtt.responsedto.ApiResponse;
import com.dtt.service.iface.DashBoardIface;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

	@Autowired
	DashBoardIface dashBoardIface;

	@GetMapping("/rate-cards")
	public ApiResponse<?> getDashBoardRateCard() {
		return dashBoardIface.getDashboardRateCards();
	}
}
