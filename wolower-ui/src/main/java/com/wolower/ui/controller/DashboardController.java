package com.wolower.ui.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wolower.ui.model.Header;
import com.wolower.ui.model.Transaction;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@GetMapping("/dashboard")
	public String index(Model model, HttpServletRequest request) {
		Header header = new Header(sessionService);
		model.addAttribute("header", header);
		model.addAttribute("name", header.getName());
		
		if (userService.getFirstTime()) {
			model.addAttribute("firstTime", userService.getFirstTime());
		}
		
		model.addAttribute("transactions", new ArrayList<Transaction>());

		return "/views/dashboard";
	}
}
