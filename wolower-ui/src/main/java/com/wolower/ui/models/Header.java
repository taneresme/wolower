package com.wolower.ui.models;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wolower.persistence.model.SocialConnection;
import com.wolower.persistence.model.User;
import com.wolower.ui.services.SocialConnectionService;

public class Header {
	private String username;
	private String profilePictureUrl;

	public Header(SocialConnectionService socialConnectionService) {
		if (SecurityContextHolder.getContext() == null)
			return;

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.username = user.getDisplayName();

		SocialConnection sc = socialConnectionService.getSocialConnection(user);
		this.profilePictureUrl = sc.getImageUrl();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

}
