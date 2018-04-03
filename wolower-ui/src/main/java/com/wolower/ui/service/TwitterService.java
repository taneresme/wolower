package com.wolower.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import com.wolower.ui.config.TwitterConfig;

@Service
public class TwitterService {
	private Twitter twitter;

	@Autowired
	public TwitterService(TwitterConfig twitterConfig) {
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
