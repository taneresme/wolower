package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wolower.ui.service.DashboardService;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService user;

	@Autowired
	private HeaderService header;

	@Autowired
	private DashboardService dashboard;

	@GetMapping("/dashboard")
	public String get(Model model, HttpServletRequest request) {
		/* Set header */
		model.addAttribute("header", header.getHeader());
		model.addAttribute("firstTime", user.getFirstTime());
		model.addAttribute("dashboard", dashboard.getDashboard());
		
		return "/views/dashboard";
	}
}
