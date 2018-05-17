package com.wolower.ui.service;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.model.Transaction;
import com.wolower.persistence.model.User;
import com.wolower.ui.model.Dashboard;
import com.wolower.ui.model.Header;
import com.wolower.ui.util.PriceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceTest {
	@Mock
	private HeaderService headerService;
	@Mock
	private ProductDao productDao;
	@Mock
	private TransactionDao transactionDao;
	@Mock
	private SessionService sessionService;
	@Mock
	private OrderDao orderDao;
	@InjectMocks
	private DashboardService service;

	@Test
	public void testGetDashboard_NoTransactionExists(){
		User user = mock(User.class);
		Header header = mock(Header.class);
		String name = "NAME";
		int id = 1;
		Long count = 10L;
		doReturn(header).when(headerService).getHeader();
		doReturn(name).when(header).getName();
		doReturn(user).when(sessionService).user();
		doReturn(id).when(user).getId();
		doReturn(count).when(productDao).countByUserIdAndSold(id, false);
		doReturn(count).when(orderDao).countByUserId(id);
		doReturn(Long.valueOf(0)).when(transactionDao).countByUserId(id);

		Dashboard result = service.getDashboard();

		assertEquals(name, result.getName());
		assertEquals(count, result.getActiveSales());
		assertEquals(count, result.getTotalOrders());
		assertEquals(Double.valueOf(0), result.getTotalPurchase());
		verify(transactionDao, never()).totalPurchaseByUserId(anyInt());
		verify(transactionDao, never()).findTop5ByUserId(anyInt());
	}

	@Test
	public void testGetDashboard_TransactionExists(){
		User user = mock(User.class);
		Header header = mock(Header.class);
		String name = "NAME";
		int id = 1;
		Long count = 10L;
		Long amount = 100L;
		String currency = "USD";
		List<Transaction> transactions = Stream.of(
				new Transaction(amount, currency),
				new Transaction(amount, currency),
				new Transaction(amount, currency),
				new Transaction(amount, currency),
				new Transaction(amount, currency)
		).collect(Collectors.toList());

		doReturn(header).when(headerService).getHeader();
		doReturn(name).when(header).getName();
		doReturn(user).when(sessionService).user();
		doReturn(id).when(user).getId();
		doReturn(count).when(productDao).countByUserIdAndSold(id, false);
		doReturn(count).when(orderDao).countByUserId(id);
		doReturn(count).when(transactionDao).countByUserId(id);
		doReturn(transactions).when(transactionDao).findTop5ByUserId(id);
		doReturn(amount).when(transactionDao).totalPurchaseByUserId(id);

		Dashboard result = service.getDashboard();

		assertEquals(name, result.getName());
		assertEquals(count, result.getActiveSales());
		assertEquals(count, result.getTotalOrders());
		assertEquals(PriceUtils.toDouble(amount), result.getTotalPurchase());
		assertEquals(transactions.size(), result.getTransactions().size());
		verify(transactionDao).totalPurchaseByUserId(anyInt());
		verify(transactionDao).findTop5ByUserId(anyInt());
	}
}
