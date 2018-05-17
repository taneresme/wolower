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
	private BalanceService balance;

	@Autowired
	public ProfileService(SessionService session, MasterpassInitializationService masterpass, BalanceService balance) {
		this.session = session;
		this.masterpass = masterpass;
		this.balance = balance;
	}

	public void setProfile(Profile profile){
		this.profile = profile;
	}

	public Profile getProfile() {
		User user = session.user();
		this.profile.setUser(user);
		this.profile.setMasterpassCheckoutId(masterpass.getCheckoutId());
		this.profile.setMasterpassPairingStatus(masterpass.masterpassPairingStatus());
		this.profile.setMasterpass(masterpass.getMasterpass());
		this.profile.setBalance(balance.getBalanceString(user));

		return this.profile;
	}
}
