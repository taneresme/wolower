package com.wolower.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.User;
import com.wolower.ui.social.SocialProfile;

@Service
public class UserService {

	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User saveUser(SocialProfile profile, String oauthToken, String oauthVerifier, OAuthToken accessToken) {
		String socialUsername = profile.getSocialUsername();
		User user = userDao.findOneBySocialMediaAndSocialUserName(profile.getSocialMedia(), socialUsername);

		/* If it does not exist */
		if (user == null) {
			user = new User();
		}
		user.setSocialMedia(profile.getSocialMedia());
		user.setSocialUserName(socialUsername);
		user.setDisplayName(profile.getDisplayName());
		user.setAccessToken(accessToken.getValue());
		user.setAccessSecret(accessToken.getSecret());
		user.setImageUrl(profile.getProfileImageUrl());
		user.setBackgroundImageUrl(profile.getBackgroundImageUrl());
		user.setProfileBannerUrl(profile.getProfileBannerUrl());
		user.setOauthToken(oauthToken);
		user.setOauthVerifier(oauthVerifier);
		user.setProfileUrl(profile.getProfileUrl());
		user.setSocialMedia(profile.getSocialMedia());
		user.setSocialUserId(profile.getSocialId());
		user.setEnabled(true);
		return userDao.save(user);
	}

	public Boolean registered(SocialProfile profile) {
		/* sample screen name : wolower_payment */
		String socialUsername = profile.getSocialUsername();
		User user = userDao.findOneBySocialMediaAndSocialUserName(profile.getSocialMedia(), socialUsername);

		/* If it does not exist */
		if (user == null) {
			return false;
		}
		return true;
	}
}
