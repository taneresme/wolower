package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolower.ui.service.SessionService;

@Controller
public class IndexController {
	@Autowired
	private SessionService sessionService;
	
	@GetMapping("/")
	public String root(Model model, HttpServletRequest request) {
		if (sessionService.authenticated()) {
			model.addAttribute("authenticated", "true");
			model.addAttribute("fullName", sessionService.userDisplayName());
		}
		return "index";
	}

	@GetMapping("/index")
	public String index(Model model, @RequestParam(name = "reason", required = false) String reason,
			HttpServletRequest request) {

		if ("invalidSession".equals(reason)) {
			model.addAttribute("showWarning", true);
			model.addAttribute("warningMessage", "We had a problem with your session details. Do you think to log in again?");
		} else if ("authenticationError".equals(reason)) {
			model.addAttribute("showWarning", true);
			model.addAttribute("warningMessage", "Hmmm, there was an error when we were trying to authenticate you. Please try it again!");
		}
		else if (sessionService.authenticated()) {
			model.addAttribute("authenticated", "true");
			model.addAttribute("fullName", sessionService.userDisplayName());
		}
		
		return "index";
	}
}
