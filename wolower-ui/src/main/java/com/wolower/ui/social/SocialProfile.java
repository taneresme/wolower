package com.wolower.ui.social;

import org.springframework.stereotype.Component;

@Component
public interface SocialProfile {
	public String getSocialMedia();

	public String getDisplayName();

	public String getSocialUsername();

	public String getSocialId();

	public String getProfileImageUrl();
	
	public String getThumbnailImageUrl();

	public String getProfileUrl();

	public String getBackgroundImageUrl();

	public String getProfileBannerUrl();
}
