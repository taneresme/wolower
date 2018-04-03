package com.wolower.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

@Service
public class SocialPostScheduler {
	private TwitterService twitterService;

	@Autowired
	public SocialPostScheduler(TwitterService twitterService) {
		this.twitterService = twitterService;
	}

	@SuppressWarnings("unused")
	@Scheduled(fixedDelay = 60000)
	public void readPosts() {
		/* gets last 20 mentions */
		//List<Tweet> mentions = twitterService.getTwitter().timelineOperations().getMentions();

	}
}
