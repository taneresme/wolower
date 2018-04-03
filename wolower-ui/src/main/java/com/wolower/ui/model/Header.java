package com.wolower.ui.model;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wolower.persistence.model.User;
import com.wolower.ui.service.SessionService;

public class Header {
	private String name;
	private String fullName;
	private String profilePictureUrl;
	private String profileBannerUrl;

	public Header(SessionService session) {
		if (SecurityContextHolder.getContext() == null)
			return;

		User user = session.user();
		this.fullName = session.userDisplayName();
		this.profilePictureUrl = user.getImageUrl();
		this.profileBannerUrl = user.getProfileBannerUrl();

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

	public String getProfileBannerUrl() {
		return profileBannerUrl;
	}

}
