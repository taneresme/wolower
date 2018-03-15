package com.wolower.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TwitterController {
	@GetMapping("/sign-in-with-twitter")
	public String signInWithTwitter() {
		
		
		return "";
	}
}