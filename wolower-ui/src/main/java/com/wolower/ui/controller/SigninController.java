package com.wolower.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.wolower.ui.services.TwitterService;

@Controller
public class SigninController {
	@Autowired
	private TwitterService twitterService;

	@GetMapping(value = "/signup/twitter")
	public String twitterSignup(WebRequest request) {
		String value = twitterService.getRequestToken();
		return String.format("redirect:https://api.twitter.com/oauth/authenticate?oauth_token=%s", value);
	}

	@GetMapping("/signup/twitter-callback")
	public String twitterCallback(@RequestParam("oauth_token") String oauthToken,
			@RequestParam("oauth_verifier") String oauthVerifier) {
		twitterService.initTwitter(oauthToken, oauthVerifier);
		return "redirect:/dashboard";
	}
}
