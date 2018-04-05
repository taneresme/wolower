package com.wolower.ui.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.wolower.persistence.enums.PostTypes;
import com.wolower.persistence.model.SocialPost;
import com.wolower.ui.contract.SocialService;
import com.wolower.ui.util.Extractor;

@Service
public class TwitterService implements SocialService {
	private static String SOCIAL_MEDIA = "TWITTER";

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
				post.setCurrency("USD");
				post.setPostDate(LocalDateTime.ofInstant(tweet.getCreatedAt().toInstant(), ZoneId.systemDefault()));
				post.setPostId(tweet.getId());
				post.setPostText(tweet.getText());
				post.setPostType(tweet.getInReplyToStatusId() == null ? PostTypes.SELLING : PostTypes.BUYING);
				post.setSocialMedia(SOCIAL_MEDIA);
				post.setSocialUserId(tweet.getFromUserId());
				posts.add(post);
			}
		}

		return posts;
	}

	private Boolean checkTweet(Tweet tweet) {
		if (tweet.getFromUser() == "wolower_payment") {
			return false;
		}
		/* Can we extract amount from tweet? */
		if (Extractor.extractPrice(tweet.getText()) == null) {
			reply(tweet, "I cannot extract price from your tweet. Please type your price like $25,50");
			return false;
		}
		return true;
	}

	private Tweet reply(Tweet tweet, String reply) {
		reply = String.format("@%s %s", tweet.getFromUser(), reply);
		TweetData td = new TweetData(reply);
		td.inReplyToStatus(Long.valueOf(tweet.getId()));
		// td.inReplyToUser(tweet.getFromUserId());
		// td.inReplyToScreenName(tweet.getFromUser());
		Tweet replyTweet = twitter.timelineOperations().updateStatus(td);
		return replyTweet;
	}
}
