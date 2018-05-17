package com.wolower.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.model.Transaction;
import com.wolower.ui.model.Dashboard;
import com.wolower.ui.model.Dashboard.TransactionModel;
import com.wolower.ui.util.PriceUtils;

@SessionScope
@Service
public class DashboardService {
	private HeaderService headerService;
	private ProductDao productDao;
	private TransactionDao transactionDao;
	private SessionService sessionService;
	private OrderDao orderDao;

	@Autowired
	public DashboardService(HeaderService headerService, ProductDao productDao, TransactionDao transactionDao,
			SessionService sessionService, OrderDao orderDao) {
		this.headerService = headerService;
		this.productDao = productDao;
		this.transactionDao = transactionDao;
		this.sessionService = sessionService;
		this.orderDao = orderDao;
	}

	public Dashboard getDashboard() {
		Dashboard dashboard = new Dashboard();
		dashboard.setName(headerService.getHeader().getName());
		dashboard.setActiveSales(productDao.countByUserIdAndSold(sessionService.user().getId(), false));
		dashboard.setTotalOrders(orderDao.countByUserId(sessionService.user().getId()));
		dashboard.setTotalPurchase(Double.valueOf(0));
		/* If there is record in the transactions table */
		if (transactionDao.countByUserId(sessionService.user().getId()) > Long.valueOf(0)) {
			dashboard.setTotalPurchase(
					PriceUtils.toDouble(transactionDao.totalPurchaseByUserId(sessionService.user().getId())));

			/* Set top 5 transactions */
			List<Transaction> transactions = transactionDao.findTop5ByUserId(sessionService.user().getId());
			List<TransactionModel> transactionModels = new ArrayList<>();
			for (Transaction transaction : transactions) {
				transactionModels.add(new TransactionModel(transaction));
			}
			dashboard.setTransactions(transactionModels);
		}
		return dashboard;
	}
}
