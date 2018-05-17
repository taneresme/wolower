package com.wolower.ui.service;

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

import com.wolower.ui.config.TwitterConfig;
import com.wolower.ui.social.SocialProfile;
import com.wolower.ui.social.TwitterSocialProfile;

@Service
public class TwitterSigninService {
	private TwitterConfig twitterConfig;
	private OAuth1Operations oauth1Operations;
	private TwitterConnectionFactory connectionFactoryTwitter;
	private OAuthToken requestToken;
	private OAuthToken accessToken;

	@Autowired
	public TwitterSigninService(TwitterConfig twitterConfig) {
		this.twitterConfig = twitterConfig;
	}

	public OAuthToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(OAuthToken accessToken) {
		this.accessToken = accessToken;
	}

	public String getAuthenticationUrl() {
		connectionFactoryTwitter = new TwitterConnectionFactory(twitterConfig.getConsumerKey(),
				twitterConfig.getConsumerSecret());
		oauth1Operations = connectionFactoryTwitter.getOAuthOperations();

		OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();

		requestToken = oauth1Operations.fetchRequestToken(twitterConfig.getRedirectUri(), null);
		String authenticateUrl = oauth1Operations.buildAuthenticateUrl(requestToken.getValue(), oAuth1Parameters);
		return authenticateUrl;
	}

	public SocialProfile initTwitter(String oauthToken, String oauthVerifier) {
		/* Obtain access token */
		accessToken = oauth1Operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier),
				null);

		/* Create twitter connection */
		Connection<Twitter> connection = connectionFactoryTwitter.createConnection(accessToken);
		Twitter twitter = connection != null ? connection.getApi() : new TwitterTemplate(accessToken.getValue());

		TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
		SocialProfile socialProfile = new TwitterSocialProfile(twitterProfile);
		return socialProfile;
	}
}
