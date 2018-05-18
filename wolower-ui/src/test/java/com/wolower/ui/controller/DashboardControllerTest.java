package com.wolower.ui.controller;

import com.wolower.ui.model.Dashboard;
import com.wolower.ui.model.Header;
import com.wolower.ui.service.DashboardService;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardControllerTest {
	@Mock
	private HeaderService headerService;

	@Mock
	private DashboardService dashboardService;
	
	@Mock
	private SessionService sessionService;

	@InjectMocks
	private DashboardController controller;

	@Test
	public void testGet(){
		Header header = mock(Header.class);
		Dashboard dashboard = mock(Dashboard.class);
		Boolean firstTime = true;
		doReturn(header).when(headerService).getHeader();
		doReturn(firstTime).when(sessionService).getFirstTime();
		doReturn(dashboard).when(dashboardService).getDashboard();

		Model model = mock(Model.class);
		String result = controller.get(model, null);

		assertEquals("/views/dashboard", result);
		verify(model).addAttribute("header", header);
		verify(model).addAttribute("firstTime", firstTime);
		verify(model).addAttribute("dashboard", dashboard);
	}
}
