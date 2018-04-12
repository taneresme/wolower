package com.wolower.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.Transaction;
import com.wolower.ui.model.Report;
import com.wolower.ui.model.Report.ProductModel;

@SessionScope
@Service
public class ReportService {
	private ProductDao productDao;
	private TransactionDao transactionDao;
	private OrderDao orderDao;
	private SessionService sessionService;

	public ReportService(ProductDao productDao, TransactionDao transactionDao, OrderDao orderDao,
			SessionService sessionService) {
		this.productDao = productDao;
		this.transactionDao = transactionDao;
		this.orderDao = orderDao;
		this.sessionService = sessionService;
	}

	public Report getReport() {
		Report report = new Report();
		int userId = sessionService.user().getId();

		List<Product> products = productDao.findAllBySoldAndUserId(false, userId);
		List<ProductModel> productModels = new ArrayList<>();
		for (Product product : products) {
			productModels.add(new ProductModel(product));
		}
		report.setProducts(productModels);

		List<Order> orders = orderDao.findAllByUserId(userId);
		report.setOrders(orders);

		List<Transaction> transactions = transactionDao.findAllByUserId(userId);
		report.setTransactions(transactions);

		return report;
	}
}
