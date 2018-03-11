package com.wolower.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/tt")
	public String test() {
		return "dene/test";
	}

}
