package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.util.StringUtils;

import com.wolower.persistence.model.User;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.service.TwitterSigninService;
import com.wolower.ui.service.UserService;
import com.wolower.ui.social.SocialProfile;

@Controller
public class SigninController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassController.class);

	@Autowired
	private TwitterSigninService twitterSigninService;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@GetMapping(value = "/twitter/signup")
	public String twitterSignup(WebRequest request) {
		String authorizeUrl = twitterSigninService.getAuthenticationUrl();

		LOGGER.info("Twitter authorizeUrl: " + authorizeUrl);

		/* Redirect to twitter authorization page */
		return String.format("redirect:%s", authorizeUrl);
	}

	@GetMapping("/twitter/callback")
	public String twitterCallback(Model model, @RequestParam(name = "oauth_token", required = false) String oauthToken,
			@RequestParam(name = "oauth_verifier", required = false) String oauthVerifier,
			@RequestParam(name = "denied", required = false) String denied, HttpServletRequest request) {
		LOGGER.info("Twitter oauthToken: " + oauthToken);
		LOGGER.info("Twitter oauthVerifier: " + oauthVerifier);
		LOGGER.info("Twitter denied: " + denied);

		/* If it not denied. */
		if (StringUtils.isEmpty(oauthToken) || StringUtils.isEmpty(oauthVerifier)) {
			return "redirect:/";
		}

		/* Initialize twitter object */
		SocialProfile profile = twitterSigninService.initTwitter(oauthToken, oauthVerifier);
		/* Save user profile */
		User user = userService.saveUser(profile, oauthToken, oauthVerifier, twitterSigninService.getAccessToken());
		/* Set session information */
		sessionService.setSession(user);

		return "redirect:/dashboard";
	}

	@GetMapping("/logout")
	public String twitterCallback(Model model, HttpServletRequest request) {
		try {
			LOGGER.info("Loging out: " + sessionService.user().getSocialUserName());
			request.logout();
		} catch (Exception ex) {
			LOGGER.error(ExceptionUtils.getStackTrace(ex));
		}
		return "";
	}
}
