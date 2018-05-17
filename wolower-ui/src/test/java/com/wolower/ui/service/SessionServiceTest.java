package com.wolower.ui.service;

import com.wolower.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {
	@Spy
	private SessionService service;

	@Test
	public void testSetSession(){
		User user = mock(User.class);

		service.setSession(user);

		assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	@Test
	public void testAuthenticated(){
		service.logout();
		Boolean result = service.authenticated();

		assertEquals(false, result);
	}

	@Test
	public void testUserFailure(){
		doReturn(false).when(service).authenticated();

		User result = service.user();

		assertEquals(null, result);
	}

	@Test
	public void testUserSuccess(){
		User user = mock(User.class);
		doReturn(true).when(service).authenticated();

		service.setSession(user);
		User result = service.user();

		assertEquals(user, result);
	}

	@Test
	public void testgetFirstTimeSuccess(){
		User user = mock(User.class);
		doReturn(LocalDateTime.now()).when(user).getTimestamp();
		doReturn(user).when(service).user();

		Boolean result = service.getFirstTime();

		assertEquals(true, result);
	}

	@Test
	public void testgetFirstTimeFailure(){
		User user = mock(User.class);
		doReturn(LocalDateTime.now().minusDays(5)).when(user).getTimestamp();
		doReturn(user).when(service).user();

		Boolean result = service.getFirstTime();

		assertEquals(false, result);
	}
}
