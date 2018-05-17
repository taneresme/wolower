package com.wolower.ui.service;

import com.wolower.ui.config.TwitterConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.oauth1.OAuthToken;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TwitterSigninServiceTest {
	@Mock
	private TwitterConfig twitterConfig;
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
