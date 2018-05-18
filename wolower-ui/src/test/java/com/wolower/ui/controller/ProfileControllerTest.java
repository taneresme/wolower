package com.wolower.ui.controller;

import com.wolower.ui.model.Header;
import com.wolower.ui.model.Profile;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.ProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {
	@Mock
	private HeaderService headerService;

	@Mock
	private ProfileService profileService;

	@InjectMocks
	private ProfileController controller;

	@Test
	public void testGet(){
		Model model = mock(Model.class);
		String tab = "TAB";
		Header header = mock(Header.class);
		Profile profile = mock(Profile.class);
		doReturn(header).when(headerService).getHeader();
		doReturn(profile).when(profileService).getProfile();

		String result = controller.get(model, tab, null);

		assertEquals("/views/profile", result);
		verify(model).addAttribute("header", header);
		verify(model).addAttribute("profile", profile);
	}
}
