package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	@GetMapping("/")
	public String root() {
		return "index";
	}

	@GetMapping("/index")
	public String index(Model model, @RequestParam(name = "reason", required = false) String reason,
			HttpServletRequest request) {
		return "index";
	}
}
