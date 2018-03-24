package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.SocialConnectionDao;
import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.SocialConnection;
import com.wolower.persistence.model.User;
import com.wolower.ui.config.TwitterConfig;

@Service
public class TwitterService {
	private final String SOCIAL_MEDIA = "TWITTER";

	@Autowired
	private TwitterConfig twitterConfig;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SocialConnectionDao socialConnectionDao;

	private OAuth1Operations oauth1Operations;
	private TwitterConnectionFactory connectionFactoryTwitter;
	private OAuthToken requestToken;
	private Twitter twitter;
	private Connection<Twitter> connection;

	public String getAuthorizeUrl() {
		connectionFactoryTwitter = new TwitterConnectionFactory(twitterConfig.getConsumerKey(),
				twitterConfig.getConsumerSecret());
		oauth1Operations = connectionFactoryTwitter.getOAuthOperations();

		OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();

		requestToken = oauth1Operations.fetchRequestToken(twitterConfig.getRedirectUri(), null);
		String authorizeUrl = oauth1Operations.buildAuthorizeUrl(requestToken.getValue(), oAuth1Parameters);
		return authorizeUrl;
	}

	public User initTwitter(String oauthToken, String oauthVerifier) {
		/* Obtain access token */
		OAuthToken accessToken = oauth1Operations
				.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);

		/* Create twitter connection */
		connection = connectionFactoryTwitter.createConnection(accessToken);
		twitter = connection != null ? connection.getApi() : new TwitterTemplate(accessToken.getValue());

		TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();

		/* Save connected user informations */
		User user = saveUser(twitterProfile);
		saveSocialConnection(user, twitterProfile, oauthToken, oauthVerifier, accessToken);
		
		return user;
	}

	private User saveUser(TwitterProfile twitterProfile) {
		String socialUserName = twitterProfile.getScreenName();
		User user = userDao.findOneBySocialMediaAndSocialUserName(SOCIAL_MEDIA, socialUserName);

		/* If it does not exist */
		if (user == null) {
			/* sample screen name : wolower_payment */
			user = new User();
			user.setSocialMedia(SOCIAL_MEDIA);
			user.setSocialUserName(socialUserName);
			user.setDisplayName(twitterProfile.getName());

			userDao.save(user);
		}
		return user;
	}

	private SocialConnection saveSocialConnection(User user, TwitterProfile twitterProfile, String oauthToken,
			String oauthVerifier, OAuthToken accessToken) {
		String socialUserId = String.valueOf(twitterProfile.getId());
		SocialConnection sc = socialConnectionDao.findOneBySocialMediaAndSocialUserId(SOCIAL_MEDIA, socialUserId);

		/* If it does not exist */
		if (sc == null) {
			/* sample screen name : wolower_payment */
			sc = new SocialConnection();
			sc.setAccessToken(accessToken.getValue());
			sc.setAccessSecret(accessToken.getSecret());
			sc.setImageUrl(twitterProfile.getProfileImageUrl());
			sc.setOauthToken(oauthToken);
			sc.setOauthVerifier(oauthVerifier);
			sc.setProfileUrl(twitterProfile.getProfileUrl());
			sc.setSocialMedia(SOCIAL_MEDIA);
			sc.settUserId(user.getId());
			sc.setSocialUserId(socialUserId);

			socialConnectionDao.save(sc);
		}

		return sc;
	}

	/* GETTERS and SETTERS */
	public Twitter getTwitter() {
		return twitter;
	}
}
