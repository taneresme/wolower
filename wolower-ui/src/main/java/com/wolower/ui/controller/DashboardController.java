package com.wolower.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String index() {
		return "/views/dashboard";
	}
}
