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

import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.User;
import com.wolower.ui.contract.SocialService;
import com.wolower.ui.social.SocialPost;
import com.wolower.ui.util.Extractor;

@Service
public class TwitterService implements SocialService {
	private static String SOCIAL_MEDIA = "TWITTER";
	private static String USER_NAME = "wolower_payment";

	private Twitter twitter;
	private UserDao userDao;

	private Long lastPostId = (long) 0;

	@Autowired
	public TwitterService(Twitter twitter, UserDao userDao) {
		this.twitter = twitter;
		this.userDao = userDao;
	}

	private Boolean checkTweet(Tweet tweet) {
		/* Whether the user is registered or not */
		User user = userDao.findOneBySocialMediaAndSocialUserName(SOCIAL_MEDIA, tweet.getFromUser());
		if (user == null) {
			/* A mention for not registered user */
			reply(tweet.getFromUser(), Long.valueOf(tweet.getId()), String.format(
					"Hi %s, you are not registered to wolower yet, please visit wolower.com. We get happy to see you among us :)",
					tweet.getUser().getScreenName()));
			return false;
		}

		/* Do not care tweets from wolower */
		if (tweet.getFromUser() == USER_NAME) {
			return false;
		}
		/* Can we extract amount from tweet? */
		if (Extractor.extractPrice(tweet.getText()) == null) {
			reply(tweet.getFromUser(), Long.valueOf(tweet.getId()),
					"I cannot extract the price from your tweet. Please type your price like $25.50");
			return false;
		}
		return true;
	}

	private SocialPost newPost(Tweet tweet) {
		SocialPost post = new SocialPost();
		post.setAmount(Extractor.extractPrice(tweet.getText()));
		post.setCurrency("USD");
		post.setPostDate(LocalDateTime.ofInstant(tweet.getCreatedAt().toInstant(), ZoneId.systemDefault()));
		post.setPostId(tweet.getId());
		post.setPostText(tweet.getText());
		/* If this is not a reply then it is a selling post */
		post.setRepliedPostId(tweet.getInReplyToStatusId());
		post.setSocialMedia(SOCIAL_MEDIA);
		post.setSocialUserId(tweet.getFromUserId());
		post.setSocialUserName(tweet.getFromUser());
		return post;
	}

	@Override
	public void post(String post) {
		twitter.timelineOperations().updateStatus(post);
	}

	@Override
	public List<SocialPost> getWoows(Long sinceId) {
		// List<Tweet> mentions = twitter.timelineOperations().getMentions();
		List<Tweet> mentions = twitter.searchOperations().search("@" + USER_NAME, 30, sinceId, Long.MAX_VALUE)
				.getTweets();
		List<SocialPost> posts = new ArrayList<>();

		for (Tweet tweet : mentions) {
			if (checkTweet(tweet)) {
				posts.add(newPost(tweet));
			}
		}

		/* Set last post ID for further uses. */
		if (mentions.size() > 0) {
			lastPostId = mentions.stream().map(x -> x.getId()).max(Long::compare).get();
		}
		return posts;
	}

	public Tweet reply(String fromUser, Long postId, String reply) {
		/* reply format would be like : @wolower_payment this is a reply */
		reply = String.format("@%s %s", fromUser, reply);
		TweetData td = new TweetData(reply);
		td.inReplyToStatus(postId);
		Tweet replyTweet = twitter.timelineOperations().updateStatus(td);
		return replyTweet;
	}

	@Override
	public SocialPost reply(SocialPost post, String reply) {
		/* reply format would be like : @wolower_payment this is a reply */
		reply = String.format("@%s %s", post.getSocialUserName(), reply);
		TweetData td = new TweetData(reply);
		td.inReplyToStatus(post.getPostId());
		Tweet replyTweet = twitter.timelineOperations().updateStatus(td);
		return newPost(replyTweet);
	}

	@Override
	public String getSocialMedia() {
		return SOCIAL_MEDIA;
	}

	@Override
	public Long getLastPostId() {
		return lastPostId;
	}
}
