package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.wolower.ui.config.TwitterConfig;


public class TwitterService {
	@Autowired
	private TwitterConfig twitterConfig;

	private Twitter twitter;

	public TwitterService() {
		this.twitter = new TwitterTemplate(twitterConfig.getConsumerKey(), twitterConfig.getConsumerSecret(),
				twitterConfig.getAccessToken(), twitterConfig.getAccessTokenSecret());
	}

	public Twitter getTwitter() {
		return twitter;
	}

	public void tweet(String tweet) {
		twitter.timelineOperations().updateStatus(tweet);
	}
}
