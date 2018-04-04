package com.wolower.ui.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wolower.ui.model.Dashboard;
import com.wolower.ui.model.Transaction;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;

	@Autowired
	private HeaderService headerService;

	@GetMapping("/dashboard")
	public String index(Model model, HttpServletRequest request) {
		model.addAttribute("header", headerService.getHeader());
		
		if (userService.getFirstTime()) {
			model.addAttribute("firstTime", userService.getFirstTime());
		}

		Dashboard dashboard = new Dashboard();
		dashboard.setName(headerService.getHeader().getName());
		model.addAttribute("dashboard", dashboard);
		
		model.addAttribute("transactions", new ArrayList<Transaction>());

		return "/views/dashboard";
	}
}
