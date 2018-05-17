package com.wolower.ui.controller;

import com.wolower.persistence.model.User;
import com.wolower.ui.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
	@Mock
	private SessionService sessionService;

	@InjectMocks
	private IndexController controller;

	@Test
	public void testRoot(){
		String displayName = "DISPLAY-NAME";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(displayName).when(user).getDisplayName();
		doReturn(false).when(sessionService).authenticated();

		Model model = mock(Model.class);
		String result = controller.root(model, null);

		assertEquals("index", result);
		verify(model, never()).addAttribute(anyString(), anyObject());
	}

	@Test
	public void testRoot_Authenticated(){
		String displayName = "DISPLAY-NAME";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(displayName).when(user).getDisplayName();
		doReturn(true).when(sessionService).authenticated();

		Model model = mock(Model.class);
		String result = controller.root(model, null);

		assertEquals("index", result);
		verify(model).addAttribute("authenticated", "true");
		verify(model).addAttribute("fullName", displayName);
	}

	@Test
	public void testIndex(){
		doReturn(false).when(sessionService).authenticated();

		Model model = mock(Model.class);
		String result = controller.index(model,null, null);

		assertEquals("index", result);
		verify(model, never()).addAttribute(anyString(), anyObject());
	}

	@Test
	public void testIndex_InvalidSession(){
		testIndex_WithReason("invalidSession");
	}

	@Test
	public void testIndex_AuthenticationError(){
		testIndex_WithReason("authenticationError");
	}

	private void testIndex_WithReason(String reason){
		doReturn(false).when(sessionService).authenticated();

		Model model = mock(Model.class);
		String result = controller.index(model,reason, null);

		assertEquals("index", result);
		verify(model, times(2)).addAttribute(anyString(), anyObject());
		verify(model).addAttribute(eq("showWarning"), anyObject());
		verify(model).addAttribute(eq("warningMessage"), anyObject());
	}

	@Test
	public void testIndex_Authenticated(){
		String displayName = "DISPLAY-NAME";
		User user = mock(User.class);
		doReturn(user).when(sessionService).user();
		doReturn(displayName).when(user).getDisplayName();
		doReturn(true).when(sessionService).authenticated();

		Model model = mock(Model.class);
		String result = controller.index(model, null, null);

		assertEquals("index", result);
		verify(model, times(2)).addAttribute(anyString(), anyObject());
		verify(model).addAttribute(eq("authenticated"), eq("true"));
		verify(model).addAttribute("fullName", displayName);
	}
}
