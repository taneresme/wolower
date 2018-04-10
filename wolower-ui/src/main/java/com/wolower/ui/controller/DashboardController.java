package com.wolower.ui.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.TransactionDao;
import com.wolower.ui.model.Dashboard;
import com.wolower.ui.model.Transaction;
import com.wolower.ui.service.HeaderService;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.service.UserService;

@Controller
public class DashboardController {
	@Autowired
	private UserService userService;

	@Autowired
	private HeaderService headerService;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private SessionService sessionService;

	@GetMapping("/dashboard")
	public String index(Model model, HttpServletRequest request) {
		/* Set header */
		model.addAttribute("header", headerService.getHeader());
		if (userService.getFirstTime()) {
			model.addAttribute("firstTime", userService.getFirstTime());
		}

		Dashboard dashboard = new Dashboard();
		dashboard.setName(headerService.getHeader().getName());
		dashboard.setActiveSales(productDao.countByUserIdAndSold(sessionService.user().getId(), false));
		dashboard.setTotalTransactions(transactionDao.countByUserId(sessionService.user().getId()));
		dashboard.setTotalPurchase(Long.valueOf(0));
		if (transactionDao.countByUserId(sessionService.user().getId()) > Long.valueOf(0)) {
			dashboard.setTotalPurchase(transactionDao.totalPurchaseByUserId(sessionService.user().getId()));
		}
		model.addAttribute("dashboard", dashboard);

		/* Product count */

		model.addAttribute("transactions", new ArrayList<Transaction>());

		return "/views/dashboard";
	}
}
