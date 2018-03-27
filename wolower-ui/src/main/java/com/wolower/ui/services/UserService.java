package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.User;
import com.wolower.ui.social.SocialProfile;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	private Boolean firstTime = false;

	public Boolean getFirstTime() {
		return firstTime;
	}

	public User saveUser(SocialProfile profile) {
		/* sample screen name : wolower_payment */
		String socialUsername = profile.getSocialUsername();
		User user = userDao.findOneBySocialMediaAndSocialUserName(profile.getSocialMedia(), socialUsername);

		/* If it does not exist */
		if (user == null) {
			user = new User();
			firstTime = true;
		}
		user.setSocialMedia(profile.getSocialMedia());
		user.setSocialUserName(socialUsername);
		user.setDisplayName(profile.getDisplayName());
		return userDao.save(user);
	}
}
