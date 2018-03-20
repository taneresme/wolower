package com.wolower.ui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwitterConfig {
	@Value("${wolower.twitter-api.consumer-key}")
	private String consumerKey;
	@Value("${wolower.twitter-api.consumer-secret}")
	private String consumerSecret;
	@Value("${wolower.twitter-api.redirect-uri}")
	private String redirectUri;

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
