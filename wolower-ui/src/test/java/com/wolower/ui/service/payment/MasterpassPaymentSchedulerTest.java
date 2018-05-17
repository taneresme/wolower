package com.wolower.ui.service.payment;

import com.mastercard.merchant.checkout.model.Address;
import com.mastercard.merchant.checkout.model.Card;
import com.mastercard.merchant.checkout.model.PaymentData;
import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.*;
import com.wolower.ui.service.BalanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MasterpassPaymentSchedulerTest {

	@Mock
	private MasterpassService masterpass;
	@Mock
	private OrderDao orderDao;
	@Mock
	private TransactionDao transactionDao;
	@Mock
	private UserDao userDao;
	@Mock
	private PspService psp;
	@Mock
	private ProductDao productDao;
	@Mock
	private BalanceService balanceService;
	@InjectMocks
	@Spy
	private MasterpassPaymentScheduler service;

	@Test
	public void testPayment_WithNoOrder(){
		List<Order> orders = new ArrayList<>();

		doReturn(orders).when(orderDao).findAllByPaid(any());

		service.payment();

		verify(orderDao, times(1)).save(anyList());
		verify(masterpass, never()).expressCheckout(any(), any(), any());
		verify(psp, never()).authorize(any());
		verify(masterpass, never()).postback(any(), any(), any(), any(), any());
		verify(productDao, never()).findOne(anyInt());
		verify(productDao, never()).save(anyList());
		verify(userDao, never()).findOne(any());
		verify(balanceService, never()).updateBalance(any(), any());
	}

	@Test
	public void testPayment_WithOrders(){
		Order order = mock(Order.class);
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		User user = mock(User.class);
		String currency = "USD";
		Long price = 10000L;
		PaymentData paymentData = mock(PaymentData.class);
		PspServiceResponse pspServiceResponse = mock(PspServiceResponse.class);
		String responseCode = "00";
		Product product = mock(Product.class);

		doReturn(orders).when(orderDao).findAllByPaid(any());
		doReturn(user).when(userDao).findOne(anyInt());
		doReturn(currency).when(order).getCurrency();
		doReturn(price).when(order).getPrice();
		doReturn(paymentData).when(masterpass).expressCheckout(any(), any(), any());
		doReturn(pspServiceResponse).when(psp).authorize(any());
		doReturn(responseCode).when(pspServiceResponse).getResponseCode();
		doNothing().when(service).saveTransaction(any(), any(), any(), any());
		doReturn(product).when(productDao).findOne(anyInt());

		service.payment();

		verify(userDao, times(2)).findOne(anyInt());
		verify(masterpass, times(1)).expressCheckout(any(), any(), any());
		verify(masterpass, times(1)).postback(any(), any(), any(), any(), any());
		verify(service, times(1)).saveTransaction(any(), any(), any(), any());
		verify(order, times(1)).setPaid(any());
		verify(orderDao, times(1)).save(anyList());
		verify(productDao, times(1)).findOne(anyInt());
		verify(product, times(1)).setSold(any());
	}

	@Test
	public void testSaveTransaction(){
		Order order = mock(Order.class);
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		User user = mock(User.class);
		String currency = "USD";
		Long price = 10000L;
		int orderId = 1;
		PaymentData paymentData = mock(PaymentData.class);
		PspServiceResponse pspServiceResponse = mock(PspServiceResponse.class);
		String socialMedia = "twitter";
		Long userId = 1000L;
		String userName = "userName";
		int id = 2;
		String pairingId = "pairingId";
		String pspTransactionId = "pspTransactionId";
		Card card = mock(Card.class);
		Address address = mock(Address.class);
		String accountNumber = "accountNumber";
		Masterpass mockMp = mock(Masterpass.class);

		doReturn(mockMp).when(masterpass).getMasterpass(any());
		doReturn(price).when(order).getPrice();
		doReturn(currency).when(order).getCurrency();
		doReturn(orderId).when(order).getId();
		doReturn(socialMedia).when(order).getSocialMedia();
		doReturn(userId).when(order).getSocialUserId();
		doReturn(userName).when(order).getSocialUserName();
		doReturn(id).when(user).getId();
		doReturn(pairingId).when(paymentData).getPairingId();
		doReturn(pspTransactionId).when(paymentData).getPspTransactionId();
		doReturn(card).when(paymentData).getCard();
		doReturn(accountNumber).when(card).getAccountNumber();
		doReturn(address).when(paymentData).getShippingAddress();

		service.saveTransaction(user, order, pspServiceResponse, paymentData);

		verify(transactionDao, times(1)).save(any(Transaction.class));
	}
}
