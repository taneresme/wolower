package com.wolower.ui.contract;

import java.util.List;

import com.wolower.persistence.model.SocialPost;

public interface SocialService extends Service {
	void post(String post);
	List<SocialPost> getMentions();
}
