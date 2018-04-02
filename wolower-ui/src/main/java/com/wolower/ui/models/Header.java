package com.wolower.ui.models;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wolower.persistence.model.User;
import com.wolower.ui.services.SessionService;

public class Header {
	private String username;
	private String profilePictureUrl;

	public Header(SessionService session) {
		if (SecurityContextHolder.getContext() == null)
			return;

		User user = session.user();
		this.username = session.userDisplayName();
		this.profilePictureUrl = user.getImageUrl();
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
