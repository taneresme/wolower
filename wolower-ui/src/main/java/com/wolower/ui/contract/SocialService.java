package com.wolower.ui.contract;

import java.util.List;

import com.wolower.ui.social.SocialPost;

public interface SocialService extends Service {
	String getSocialMedia();
	void post(String post);
	List<SocialPost> getWoows(Long sinceId);
	SocialPost reply(SocialPost post, String reply);
	Long getLastPostId();
}
