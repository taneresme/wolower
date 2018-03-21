package com.wolower.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.util.StringUtils;

import com.wolower.ui.services.TwitterService;

@Controller
public class SigninController {

	@Autowired
	private TwitterService twitterService;

	@GetMapping(value = "/twitter/signup")
	public String twitterSignup(WebRequest request) {
		String authorizeUrl = twitterService.getAuthorizeUrl();
		return String.format("redirect:%s", authorizeUrl);
	}

	@GetMapping("/twitter/callback")
	public String twitterCallback(@RequestParam(name = "oauth_token", required = false) String oauthToken,
			@RequestParam(name = "oauth_verifier", required = false) String oauthVerifier,
			@RequestParam(name = "denied", required = false) String denied) {
		/*
		 * If it is not denied.
		 */
		if (!StringUtils.isEmpty(oauthToken) && !StringUtils.isEmpty(oauthVerifier)) {
			twitterService.initTwitter(oauthToken, oauthVerifier);
			return "redirect:/dashboard";
		}
		return "redirect:/";
	}
}
