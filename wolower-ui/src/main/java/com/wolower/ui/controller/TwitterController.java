package com.wolower.ui.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TwitterController {
	@GetMapping("/sign-in-with-twitter")
	@ResponseBody
	public String signInWithTwitter() throws KeyManagementException, NoSuchAlgorithmException {
		
		String consumerKey = "..."; // The application's consumer key
		String consumerSecret = "..."; // The application's consumer secret
		String accessToken = "...";
		String accessTokenSecret = "...";

		Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		SearchResults search = twitter.searchOperations().search("taneresme");
		List<Tweet> tweets = twitter.timelineOperations().getFavorites();
		int a = tweets.size();
		
		return tweets.get(0).toString();
	}
}
