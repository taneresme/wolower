package com.wolower.ui.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.oauth1.OAuthToken;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TwitterSigninServiceTest {
	@InjectMocks
	private TwitterSigninService service;

	@Test
	public void testGetAccessToken(){
		OAuthToken token = mock(OAuthToken.class);
		service.setAccessToken(token);

		OAuthToken result = service.getAccessToken();

		assertEquals(token, result);
	}
}
