package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.ProfileService;

@Controller
public class ProfileController {
	@Autowired
	private HeaderService header;

	@Autowired
	private ProfileService profile;

	@GetMapping("/profile")
	public String get(Model model, @RequestParam(name = "tab", required = false) String tab,
			HttpServletRequest request) {
		/* Set header */
		model.addAttribute("header", header.getHeader());
		model.addAttribute("profile", profile.getProfile());

		return "/views/profile";
	}
}
