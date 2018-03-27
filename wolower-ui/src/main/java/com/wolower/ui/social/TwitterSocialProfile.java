package com.wolower.ui.social;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

@Component
public class TwitterSocialProfile implements SocialProfile {
	private final String SOCIAL_MEDIA = "TWITTER";

	private TwitterProfile profile;
	private Twitter twitter;

	public TwitterSocialProfile(TwitterProfile profile, Twitter twitter) {
		this.profile = profile;
		this.twitter = twitter;
	}

	public TwitterSocialProfile() {
	}

	@Override
	public String getSocialMedia() {
		return SOCIAL_MEDIA;
	}

	@Override
	public String getDisplayName() {
		return profile.getName();
	}

	@Override
	public String getSocialUsername() {
		return profile.getScreenName();
	}

	@Override
	public String getSocialId() {
		return String.valueOf(profile.getId());
	}

	@Override
	public String getProfileImageUrl() {
		return profile.getProfileImageUrl();
	}

	@Override
	public String getProfileUrl() {
		return profile.getProfileUrl();
	}

}