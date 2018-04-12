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

	public ReportService(ProductDao productDao, TransactionDao transactionDao, OrderDao orderDao) {
		this.productDao = productDao;
		this.transactionDao = transactionDao;
		this.orderDao = orderDao;
	}

	public Report getReport() {
		Report report = new Report();

		List<Product> products = productDao.findAllBySold(false);
		List<ProductModel> productModels = new ArrayList<>();
		for (Product product : products) {
			productModels.add(new ProductModel(product));
		}
		report.setProducts(productModels);

		List<Order> orders = orderDao.findAll();
		report.setOrders(orders);

		List<Transaction> transactions = transactionDao.findAll();
		report.setTransactions(transactions);

		return report;
	}
}
