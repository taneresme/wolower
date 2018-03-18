package com.wolower.ui.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wolower.ui.config.TwitterConfig;

@Controller
public class SigninController {
	
	@Autowired
	private TwitterConfig twitterConfig;

	@GetMapping("/signin/twitter")
	@ResponseBody
	public String signInWithTwitter() throws KeyManagementException, NoSuchAlgorithmException {
		String consumerKey = twitterConfig.getConsumerKey();
		String consumerSecret = twitterConfig.getConsumerSecret();
		Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret);
		// twitter.restOperations().
		

		SearchResults search = twitter.searchOperations().search("taneresme");
		List<Tweet> tweets = twitter.timelineOperations().getFavorites();
		int a = tweets.size();

		return tweets.get(0).toString();
	}
}
