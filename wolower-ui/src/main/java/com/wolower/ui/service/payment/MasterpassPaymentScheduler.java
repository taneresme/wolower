package com.wolower.ui.service.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mastercard.merchant.checkout.model.PaymentData;
import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.Masterpass;
import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.Transaction;
import com.wolower.persistence.model.User;
import com.wolower.ui.service.BalanceService;
import com.wolower.ui.util.PriceUtils;

@Service
public class MasterpassPaymentScheduler {

	private MasterpassService masterpass;
	private OrderDao orderDao;
	private TransactionDao transactionDao;
	private UserDao userDao;
	private PspService psp;
	private ProductDao productDao;
	private BalanceService balanceService;

	@Autowired
	public MasterpassPaymentScheduler(MasterpassService masterpass, OrderDao orderDao, TransactionDao transactionDao,
			UserDao userDao, PspService psp, ProductDao productDao, BalanceService balanceService) {
		this.masterpass = masterpass;
		this.orderDao = orderDao;
		this.transactionDao = transactionDao;
		this.userDao = userDao;
		this.psp = psp;
		this.productDao = productDao;
		this.balanceService = balanceService;
	}

	@Scheduled(fixedDelay = 60000)
	public void payment() {
		/* Get orders pending for payment */
		List<Order> orders = this.orderDao.findAllByPaid(false);
		for (Order order : orders) {
			User user = userDao.findOne(order.getUserId());

			/* Perform Masterpass checkout */
			String currency = order.getCurrency();
			Double amount = PriceUtils.toDouble(order.getPrice());
			PaymentData paymentData = masterpass.expressCheckout(user, amount, currency);

			/* Perform Psp Payment */
			PspServiceResponse pspResponse = psp.authorize(paymentData);
			Boolean paymentSuccessful = "00".equals(pspResponse.getResponseCode());
			masterpass.postback(paymentData, currency, pspResponse.getAuthorizationCode(), paymentSuccessful, amount);

			/* Save transaction */
			saveTransaction(user, order, pspResponse, paymentData);

			/* Update order as paid */
			order.setPaid(paymentSuccessful);
			orderDao.save(order);

			/* Set the product as sold */
			Product product = this.productDao.findOne(order.getProductId());
			product.setSold(true);
			this.productDao.save(product);

			/* Update balance */
			User productUser = userDao.findOne(product.getUserId());
			this.balanceService.updateBalance(productUser, order.getPrice());
		}
		this.orderDao.save(orders);
	}

	void saveTransaction(User user, Order order, PspServiceResponse pspResponse, PaymentData paymentData) {
		Masterpass masterpass = this.masterpass.getMasterpass(user);
		Transaction transaction = new Transaction();
		transaction.setAmount(order.getPrice());
		transaction.setAuthorizationCode(pspResponse.getAuthorizationCode());
		transaction.setCurrency(order.getCurrency());
		transaction.setMasterpassId(masterpass.getId());
		transaction.setOrderId(order.getId());
		transaction.setPairingId(paymentData.getPairingId());
		transaction.setSocialMedia(order.getSocialMedia());
		transaction.setSocialUserId(order.getSocialUserId());
		transaction.setSocialUserName(order.getSocialUserName());
		transaction.setUserId(user.getId());
		transaction.setResponseCode(pspResponse.getResponseCode());
		transaction.setTransactionId(paymentData.getPspTransactionId());
		transaction.setPAN(paymentData.getCard().getAccountNumber());
		transaction.setShippingAddress(paymentData.getShippingAddress().toString());

		this.transactionDao.save(transaction);
	}
}
