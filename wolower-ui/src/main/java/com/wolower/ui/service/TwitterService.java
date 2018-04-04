package com.wolower.ui.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.StatusDetails;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.wolower.persistence.model.SocialPost;
import com.wolower.ui.contract.SocialService;
import com.wolower.ui.util.Extractor;

@Service
public class TwitterService implements SocialService {
	private Twitter twitter;

	@Autowired
	public TwitterService(Twitter twitter) {
		this.twitter = twitter;
	}

	@Override
	public void post(String post) {
		twitter.timelineOperations().updateStatus(post);
	}

	@Override
	public List<SocialPost> getMentions() {
		List<Tweet> mentions = twitter.timelineOperations().getMentions();
		List<SocialPost> posts = new ArrayList<>();

		for (Tweet tweet : mentions) {
			if (checkTweet(tweet)) {
				SocialPost post = new SocialPost();
				post.setAmount(Extractor.extractPrice(tweet.getText()));
				post.setPostDate(LocalDateTime.ofInstant(tweet.getCreatedAt().toInstant(), ZoneId.systemDefault()));
				post.setPostId(String.valueOf(tweet.getId()));
			}
		}

		return posts;
	}

	private Boolean checkTweet(Tweet tweet) {
		/* Can we extract amount from tweet? */
		if (Extractor.extractPrice(tweet.getText()) == null) {
			reply(tweet.getId(), "I cannot extract price from your tweet. Please type your price like $25,50");
			return false;
		}
		return true;
	}

	private Tweet reply(Long tweetId, String reply) {
		TweetData td = new TweetData(reply);
		td.inReplyToStatus(tweetId);
		Tweet tweet = twitter.timelineOperations().updateStatus(td);
		return tweet;
	}
}
