package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.ReportService;

@Controller
public class ReportController {
	@Autowired
	private HeaderService header;

	@Autowired
	private ReportService report;

	@GetMapping("/report")
	public String get(Model model, HttpServletRequest request) {
		/* Set header */
		model.addAttribute("header", header.getHeader());
		model.addAttribute("report", report.getReport());

		return "/views/report";
	}
}
