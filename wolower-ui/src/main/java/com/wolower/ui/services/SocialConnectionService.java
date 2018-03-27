package com.wolower.ui.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.SocialConnectionDao;
import com.wolower.persistence.model.SocialConnection;
import com.wolower.persistence.model.User;
import com.wolower.ui.social.SocialProfile;

@Service("SocialConnectionService")
public class SocialConnectionService {
	@Autowired
	private SocialConnectionDao socialConnectionDao;

	public SocialConnection saveSocialConnection(User user, SocialProfile profile, String oauthToken,
			String oauthVerifier, OAuthToken accessToken) {
		String socialUserId = String.valueOf(profile.getSocialId());
		SocialConnection sc = socialConnectionDao.findOneBySocialMediaAndSocialUserId(profile.getSocialMedia(),
				socialUserId);

		/* If it does not exist */
		if (sc == null) {
			sc = new SocialConnection();
		}
		sc.setAccessToken(accessToken.getValue());
		sc.setAccessSecret(accessToken.getSecret());
		sc.setImageUrl(profile.getProfileImageUrl());
		sc.setOauthToken(oauthToken);
		sc.setOauthVerifier(oauthVerifier);
		sc.setProfileUrl(profile.getProfileUrl());
		sc.setSocialMedia(profile.getSocialMedia());
		sc.settUserId(user.getId());
		sc.setSocialUserId(socialUserId);

		return socialConnectionDao.save(sc);
	}

	public SocialConnection getSocialConnection(User user) {
		return socialConnectionDao.findOneBySocialMediaAndTUserId(user.getSocialMedia(), user.getId());
	}
}
