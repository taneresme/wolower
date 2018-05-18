package com.wolower.ui.service;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.Transaction;
import com.wolower.persistence.model.User;
import com.wolower.ui.model.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {
	@Mock
	private ProductDao productDao;
	@Mock
	private TransactionDao transactionDao;
	@Mock
	private OrderDao orderDao;
	@Mock
	private SessionService sessionService;
	@InjectMocks
	private ReportService service;

	@Test
	public void testGetReport(){
		Long price = 1000L;
		String currency = "USD";
		List<Product> products = Stream.of(
				new Product(price, currency),
				new Product(price, currency),
				new Product(price, currency),
				new Product(price, currency)
		).collect(Collectors.toList());

		List<Order> orders = Stream.of(
				new Order(price, currency),
				new Order(price, currency),
				new Order(price, currency),
				new Order(price, currency)
		).collect(Collectors.toList());

		List<Transaction> transactions = Stream.of(
				new Transaction(price, currency),
				new Transaction(price, currency),
				new Transaction(price, currency),
				new Transaction(price, currency)
		).collect(Collectors.toList());

		User user = mock(User.class);
		int id = 1;
		doReturn(id).when(user).getId();
		doReturn(user).when(sessionService).user();
		doReturn(products).when(productDao).findAllByUserId(id);
		doReturn(orders).when(orderDao).findAllByUserId(id);
		doReturn(transactions).when(transactionDao).findAllByUserId(id);

		Report result = service.getReport();

		assertEquals(products.size(), result.getProducts().size());
		assertEquals(orders.size(), result.getOrders().size());
		assertEquals(transactions.size(), result.getTransactions().size());
	}
}
