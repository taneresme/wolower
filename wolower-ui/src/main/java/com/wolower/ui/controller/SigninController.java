package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.util.StringUtils;

import com.wolower.persistence.model.User;
import com.wolower.ui.services.TwitterService;

@Controller
public class SigninController {
	@Autowired
	private TwitterService twitterService;
	
	@GetMapping(value = "/twitter/signup")
	public String twitterSignup(WebRequest request) {
		String authorizeUrl = twitterService.getAuthorizeUrl();

		/* Redirect to twitter authorization page */
		return String.format("redirect:%s", authorizeUrl);
	}

	@GetMapping("/twitter/callback")
	public String twitterCallback(Model model, @RequestParam(name = "oauth_token", required = false) String oauthToken,
			@RequestParam(name = "oauth_verifier", required = false) String oauthVerifier,
			@RequestParam(name = "denied", required = false) String denied, HttpServletRequest request) {
		/* If it is not denied. */
		if (StringUtils.isEmpty(oauthToken) || StringUtils.isEmpty(oauthVerifier)) {
			return "redirect:/";
		}

		/* Initialize twitter object */
		User user = twitterService.initTwitter(oauthToken, oauthVerifier);

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, null));

		return "redirect:/dashboard";
	}
}
