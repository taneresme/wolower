package com.wolower.ui.service;

import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.User;
import com.wolower.ui.social.SocialProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.oauth1.OAuthToken;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	@Mock
	private UserDao userDao;
	@InjectMocks
	private UserService service;

	@Test
	public void testSaveUser(){
		User user = mock(User.class);
		SocialProfile profile = mock(SocialProfile.class);
		String oauthVerifier = "oauthVerifier";
		String socialMedia = "TWITTER";
		String socialUsername = "socialUsername";
		String oauthToken = "oauthToken";
		String displayName = "displayName";
		String thumbnailUrl = "thumbnailUrl";
		String profileBannerUrl = "thumbnailUrl";
		String backgroundImageUrl = "backgroundImageUrl";
		String profileImageUrl = "profileImageUrl";
		String profileUrl = "profileUrl";
		String socialId = "socialId";
		String accessTokenValue = "accessTokenValue";
		String accessTokenSecret = "accessTokenSecret";
		OAuthToken token = mock(OAuthToken.class);
		doReturn(socialMedia).when(profile).getSocialMedia();
		doReturn(socialUsername).when(profile).getSocialUsername();
		doReturn(displayName).when(profile).getDisplayName();
		doReturn(profileImageUrl).when(profile).getProfileImageUrl();
		doReturn(thumbnailUrl).when(profile).getThumbnailImageUrl();
		doReturn(backgroundImageUrl).when(profile).getBackgroundImageUrl();
		doReturn(profileBannerUrl).when(profile).getProfileBannerUrl();
		doReturn(profileUrl).when(profile).getProfileUrl();
		doReturn(socialMedia).when(profile).getSocialMedia();
		doReturn(socialId).when(profile).getSocialId();
		doReturn(accessTokenValue).when(token).getValue();
		doReturn(accessTokenSecret).when(token).getSecret();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(socialMedia, socialUsername);

		User result = service.saveUser(profile, oauthToken, oauthVerifier, token);

		verify(user).setSocialMedia(socialMedia);
		verify(user).setSocialUserName(socialUsername);
		verify(user).setDisplayName(displayName);
		verify(user).setAccessToken(accessTokenValue);
		verify(user).setAccessSecret(accessTokenSecret);
		verify(user).setImageUrl(profileImageUrl);
		verify(user).setThumbnailUrl(thumbnailUrl);
		verify(user).setBackgroundImageUrl(backgroundImageUrl);
		verify(user).setProfileBannerUrl(profileBannerUrl);
		verify(user).setOauthToken(oauthToken);
		verify(user).setOauthVerifier(oauthVerifier);
		verify(user).setProfileUrl(profileUrl);
		verify(user).setSocialUserId(socialId);
		verify(user).setEnabled(true);
	}

	@Test
	public void testRegistred_True(){
		User user = mock(User.class);
		SocialProfile profile = mock(SocialProfile.class);
		String socialMedia = "TWITTER";
		String socialUsername = "socialUsername";
		doReturn(socialMedia).when(profile).getSocialMedia();
		doReturn(socialUsername).when(profile).getSocialUsername();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(socialMedia, socialUsername);

		Boolean result = service.registered(profile);

		assertEquals(true, result);
	}

	@Test
	public void testRegistred_False(){
		SocialProfile profile = mock(SocialProfile.class);
		String socialMedia = "TWITTER";
		String socialUsername = "socialUsername";
		doReturn(socialMedia).when(profile).getSocialMedia();
		doReturn(socialUsername).when(profile).getSocialUsername();
		doReturn(null).when(userDao).findOneBySocialMediaAndSocialUserName(socialMedia, socialUsername);

		Boolean result = service.registered(profile);

		assertEquals(false, result);
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
