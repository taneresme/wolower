package com.wolower.ui.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.ui.model.Dashboard;

@SessionScope
@Service
public class DashboardService {
	private Dashboard dashboard;
	private HeaderService headerService;
	private ProductDao productDao;
	private TransactionDao transactionDao;
	private SessionService sessionService;

	public DashboardService(HeaderService headerService, ProductDao productDao, TransactionDao transactionDao,
			SessionService sessionService) {
		this.headerService = headerService;
		this.productDao = productDao;
		this.transactionDao = transactionDao;
		this.sessionService = sessionService;
	}

	public Dashboard getDashboard() {
		dashboard = new Dashboard();
		dashboard.setName(headerService.getHeader().getName());
		dashboard.setActiveSales(productDao.countByUserIdAndSold(sessionService.user().getId(), false));
		dashboard.setTotalTransactions(transactionDao.countByUserId(sessionService.user().getId()));
		dashboard.setTotalPurchase(Long.valueOf(0));
		/* If there is record in the transactions table */
		if (transactionDao.countByUserId(sessionService.user().getId()) > Long.valueOf(0)) {
			dashboard.setTotalPurchase(transactionDao.totalPurchaseByUserId(sessionService.user().getId()));
			/* Set top 20 transactions */
			dashboard.setTransactions(transactionDao.findTop20ByUserId(sessionService.user().getId()));
		}
		return dashboard;
	}
}
