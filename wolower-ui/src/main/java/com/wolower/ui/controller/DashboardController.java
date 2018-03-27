package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolower.ui.models.Header;
import com.wolower.ui.services.SocialConnectionService;
import com.wolower.ui.services.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;

	@Autowired
	private SocialConnectionService socialConnectionService;

	@GetMapping("/dashboard")
	public String index(Model model, @RequestParam(name = "firstTime", required = false) String firstTime,
			HttpServletRequest request) {
		Header header = new Header(socialConnectionService);

		model.addAttribute("header", header);
		model.addAttribute("username", header.getUsername());

		if (userService.getFirstTime()) {
			model.addAttribute("firstTime", firstTime);
		}

		return "/views/dashboard";
	}
}
