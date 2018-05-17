package com.wolower.ui.controller;

import com.wolower.persistence.model.User;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.service.TwitterSigninService;
import com.wolower.ui.service.UserService;
import com.wolower.ui.social.SocialProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SigninControllerTest {
	@Mock
	private TwitterSigninService twitterSigninService;
	@Mock
	private UserService userService;
	@Mock
	private SessionService sessionService;
	@Mock
	private HttpServletRequest httpServletRequest;
	@InjectMocks
	private SigninController controller;

	@Test
	public void testTwitterSignup(){
		String authorizeUrl = "AUTHORIZE-URL";
		doReturn(authorizeUrl).when(twitterSigninService).getAuthenticationUrl();

		String result = controller.twitterSignup(null);

		assertEquals(String.format("redirect:%s", authorizeUrl), result);
		verify(twitterSigninService).getAuthenticationUrl();
	}

	@Test
	public void testTwitterCallbackDenied(){
		Model model = mock(Model.class);
		String oauthToken = "";
		String oauthVerifier = "oauthVerifier";
		String denied = "denied";

		String result = controller.twitterCallback(model, oauthToken, oauthVerifier, denied, null);

		assertEquals("redirect:/", result);
		verify(twitterSigninService, never()).initTwitter(oauthToken, oauthVerifier);
		verify(userService, never()).saveUser(anyObject(), anyString(), anyString(), anyObject());
		verify(sessionService, never()).setSession(anyObject());
	}

	@Test
	public void testTwitterCallbackNotDenied(){
		Model model = mock(Model.class);
		String oauthToken = "oauthToken";
		String oauthVerifier = "oauthVerifier";
		String denied = "not-denied";
		SocialProfile profile = mock(SocialProfile.class);
		User user = mock(User.class);
		OAuthToken token = mock(OAuthToken.class);
		doReturn(profile).when(twitterSigninService).initTwitter(oauthToken, oauthVerifier);
		doReturn(token).when(twitterSigninService).getAccessToken();
		doReturn(user).when(userService).saveUser(profile, oauthToken, oauthVerifier, token);

		String result = controller.twitterCallback(model, oauthToken, oauthVerifier, denied, null);

		assertEquals("redirect:/dashboard", result);
		verify(twitterSigninService).initTwitter(oauthToken, oauthVerifier);
		verify(userService).saveUser(profile, oauthToken, oauthVerifier, token);
		verify(sessionService).setSession(user);
	}

	@Test
	public void testLogout() throws ServletException {
		String displayName = "DISPLAY-NAME";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(displayName).when(user).getDisplayName();

		String result = controller.logout(null, httpServletRequest);

		assertEquals("", result);
		verify(httpServletRequest).logout();
	}
}
