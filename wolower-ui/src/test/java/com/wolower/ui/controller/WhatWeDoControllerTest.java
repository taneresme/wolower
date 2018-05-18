package com.wolower.ui.controller;

import com.wolower.ui.model.Header;
import com.wolower.ui.service.HeaderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WhatWeDoControllerTest {
	@Mock
	private HeaderService headerService;

	@InjectMocks
	private WhatWeDoController controller;

	@Test
	public void testPage(){
		Model model = mock(Model.class);
		Header header = mock(Header.class);
		doReturn(header).when(headerService).getHeader();

		String result = controller.page(model);

		assertEquals("/views/what-we-do", result);
		verify(model).addAttribute("header", header);
	}
}
