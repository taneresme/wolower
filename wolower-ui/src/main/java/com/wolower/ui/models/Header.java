package com.wolower.ui.models;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wolower.persistence.model.User;
import com.wolower.ui.services.SessionService;

public class Header {
	private String name;
	private String fullName;
	private String profilePictureUrl;

	public Header(SessionService session) {
		if (SecurityContextHolder.getContext() == null)
			return;

		User user = session.user();
		this.fullName = session.userDisplayName();
		this.profilePictureUrl = user.getImageUrl();
		
		try {
			this.name = this.fullName.split(" ")[0];
		} catch (Throwable ex) {
			this.name = this.fullName;
		}
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

}
