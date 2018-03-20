package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.wolower.ui.config.TwitterConfig;

@Service
public class TwitterService {
	@Autowired
	private TwitterConfig twitterConfig;

	private OAuth1Operations oauth1Operations;
	private TwitterConnectionFactory connectionFactoryTwitter;
	private OAuthToken requestToken;
	private Twitter twitter;
	private Connection<Twitter> connection;
	
	public String getRequestToken() {
		connectionFactoryTwitter = new TwitterConnectionFactory(twitterConfig.getConsumerKey(),
				twitterConfig.getConsumerSecret());
		oauth1Operations = connectionFactoryTwitter.getOAuthOperations();

		//OAuth1Parameters oAuth1Parameters = new OAuth1Parameters();

		requestToken = oauth1Operations.fetchRequestToken(twitterConfig.getRedirectUri(), null);
		//String authorizeUrl = oauth1Operations.buildAuthorizeUrl(requestToken.getValue(), oAuth1Parameters);
		return requestToken.getValue();
	}

	public void initTwitter(String oauthToken, String oauthVerifier) {
		OAuthToken accessToken = oauth1Operations
				.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);
		
		connection = connectionFactoryTwitter.createConnection(accessToken);
		twitter = connection != null ? connection.getApi() : new TwitterTemplate(accessToken.getValue());

		//TwitterProfile twitterProfile = twitter.userOperations().getUserProfile();
		//ConnectionKey connectionKey = connection.getKey();
	}

}
