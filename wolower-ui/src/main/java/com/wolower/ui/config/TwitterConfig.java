package com.wolower.ui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterConfig {
	@Value("${wolower.twitter-api.consumer-key}")
	private String consumerKey;
	@Value("${wolower.twitter-api.consumer-secret}")
	private String consumerSecret;
	@Value("${wolower.twitter-api.access-token}")
	private String accessToken;
	@Value("${wolower.twitter-api.access-token-secret}")
	private String accessTokenSecret;
	@Value("${wolower.twitter-api.redirect-uri}")
	private String redirectUri;

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

}
