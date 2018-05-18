package com.wolower.ui.controller;

import com.wolower.ui.model.Header;
import com.wolower.ui.model.Report;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportControllerTest {
	@Mock
	private HeaderService headerService;

	@Mock
	private ReportService reportService;

	@InjectMocks
	private ReportController controller;

	@Test
	public void testGet(){
		Model model = mock(Model.class);
		Header header = mock(Header.class);
		Report report = mock(Report.class);
		doReturn(header).when(headerService).getHeader();
		doReturn(report).when(reportService).getReport();

		String result = controller.get(model, null);

		assertEquals("/views/report", result);
		verify(model).addAttribute("header", header);
		verify(model).addAttribute("report", report);
	}
}
