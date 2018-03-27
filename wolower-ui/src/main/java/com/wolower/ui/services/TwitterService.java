package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class TwitterService {
	@Autowired
	private TwitterConfig twitterConfig;

	private OAuth1Operations oauth1Operations;
	private TwitterConnectionFactory connectionFactoryTwitter;
	private OAuthToken requestToken;
	private Connection<Twitter> connection;
	private String oauthToken;
	private String oauthVerifier;
	private OAuthToken accessToken;
	private SocialProfile socialProfile;

	public OAuthToken getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(OAuthToken requestToken) {
		this.requestToken = requestToken;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getOauthVerifier() {
		return oauthVerifier;
	}

	public void setOauthVerifier(String oauthVerifier) {
		this.oauthVerifier = oauthVerifier;
	}

	public OAuthToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(OAuthToken accessToken) {
		this.accessToken = accessToken;
	}

	public String getAuthorizeUrl() {
		connectionFactoryTwitter = new TwitterConnectionFactory(twitterConfig.getConsumerKey(),
				twitterConfig.getConsumerSecret());
		oauth1Operations = connectionFactoryTwitter.getOAuthOperations();

		OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();

		requestToken = oauth1Operations.fetchRequestToken(twitterConfig.getRedirectUri(), null);
		String authorizeUrl = oauth1Operations.buildAuthorizeUrl(requestToken.getValue(), oAuth1Parameters);
		return authorizeUrl;
	}

	public SocialProfile initTwitter(String oauthToken, String oauthVerifier) {
		/* Obtain access token */
		accessToken = oauth1Operations
				.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);

		/* Create twitter connection */
		connection = connectionFactoryTwitter.createConnection(accessToken);
		Twitter twitter = connection != null ? connection.getApi() : new TwitterTemplate(accessToken.getValue());

		TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
		socialProfile = new TwitterSocialProfile(twitterProfile, twitter);
		return socialProfile;
	}
}
