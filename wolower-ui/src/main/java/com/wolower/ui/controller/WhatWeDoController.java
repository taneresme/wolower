package com.wolower.ui.controller;

import com.wolower.ui.service.HeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WhatWeDoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassController.class);

	@Autowired
	private HeaderService header;

	@GetMapping(value = "/what-we-do")
	public String page(Model model) {
		/* Set header */
		model.addAttribute("header", header.getHeader());
		return "/views/what-we-do";
	}
}
