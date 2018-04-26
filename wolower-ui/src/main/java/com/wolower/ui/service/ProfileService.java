package com.wolower.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.model.User;
import com.wolower.ui.model.Profile;

@SessionScope
@Service
public class ProfileService {
	private Profile profile = new Profile();
	private SessionService session;
	private MasterpassInitializationService masterpass;

	@Autowired
	public ProfileService(SessionService session, MasterpassInitializationService masterpass) {
		this.session = session;
		this.masterpass = masterpass;
	}

	public Profile getProfile() {
		User user = session.user();
		this.profile.setUser(user);
		this.profile.setMasterpassCheckoutId(masterpass.getCheckoutId());
		this.profile.setMasterpassPairingStatus(masterpass.masterpassPairingStatus());
		this.profile.setMasterpass(masterpass.getMasterpass());

		return this.profile;
	}
}