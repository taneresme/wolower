package com.wolower.ui.service;

import com.wolower.persistence.model.User;
import com.wolower.ui.model.Header;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class HeaderServiceTest {
	@Mock
	private SessionService sessionService;
	@Mock
	private BalanceService balanceService;
	@InjectMocks
	private HeaderService service;

	@Test
	public void testHeaderSingleName(){
		User user = mock(User.class);
		String displayName = "displayName";
		String thumbnailUrl = "thumbnailUrl";
		String profileBannerUrl = "thumbnailUrl";
		String backgroundImageUrl = "backgroundImageUrl";
		String balanceString = "balanceString";
		doReturn(displayName).when(user).getDisplayName();
		doReturn(thumbnailUrl).when(user).getThumbnailUrl();
		doReturn(profileBannerUrl).when(user).getProfileBannerUrl();
		doReturn(backgroundImageUrl).when(user).getBackgroundImageUrl();
		doReturn(user).when(sessionService).user();
		doReturn(balanceString).when(balanceService).getBalanceString(user);

		Header result = service.getHeader();

		assertEquals(displayName, result.getName());
		assertEquals(displayName, result.getFullName());
		assertEquals(thumbnailUrl, result.getProfilePictureUrl());
		assertEquals(profileBannerUrl, result.getProfileBannerUrl());
		assertEquals(backgroundImageUrl, result.getBackgroundImageUrl());
		assertEquals(balanceString, result.getBalance());
	}

	@Test
	public void testHeaderNameAndSurname(){
		User user = mock(User.class);
		String name = "name";
		String surname = "surname";
		String displayName = name + " " + surname;
		String thumbnailUrl = "thumbnailUrl";
		String profileBannerUrl = "thumbnailUrl";
		String backgroundImageUrl = "backgroundImageUrl";
		String balanceString = "balanceString";
		doReturn(displayName).when(user).getDisplayName();
		doReturn(thumbnailUrl).when(user).getThumbnailUrl();
		doReturn(profileBannerUrl).when(user).getProfileBannerUrl();
		doReturn(backgroundImageUrl).when(user).getBackgroundImageUrl();
		doReturn(user).when(sessionService).user();
		doReturn(balanceString).when(balanceService).getBalanceString(user);

		Header result = service.getHeader();

		assertEquals(name, result.getName());
		assertEquals(displayName, result.getFullName());
		assertEquals(thumbnailUrl, result.getProfilePictureUrl());
		assertEquals(profileBannerUrl, result.getProfileBannerUrl());
		assertEquals(backgroundImageUrl, result.getBackgroundImageUrl());
		assertEquals(balanceString, result.getBalance());
	}
}
