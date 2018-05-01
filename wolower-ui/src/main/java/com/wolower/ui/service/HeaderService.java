package com.wolower.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.model.User;
import com.wolower.ui.model.Header;

@SessionScope
@Service
public class HeaderService {
	private Header header = new Header();
	private SessionService session;
	private BalanceService balanceService;

	@Autowired
	public HeaderService(SessionService session, BalanceService balanceService) {
		this.session = session;
		this.balanceService = balanceService;
	}

	public Header getHeader() {
		User user = session.user();
		this.header.setFullName(user.getDisplayName());
		this.header.setProfilePictureUrl(user.getThumbnailUrl());
		this.header.setProfileBannerUrl(user.getProfileBannerUrl());
		this.header.setBackgroundImageUrl(user.getBackgroundImageUrl());
		this.header.setBalance(balanceService.getBalanceString(user));

		try {
			this.header.setName(this.header.getFullName().split(" ")[0]);
		} catch (Throwable ex) {
			this.header.setName(this.header.getFullName());
		}

		return this.header;
	}
}
