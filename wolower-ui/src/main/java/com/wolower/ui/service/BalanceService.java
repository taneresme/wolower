package com.wolower.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.BalanceDao;
import com.wolower.persistence.model.Balance;
import com.wolower.persistence.model.User;
import com.wolower.ui.util.PriceUtils;

@Service
public class BalanceService {
	private BalanceDao balanceDao;

	@Autowired
	public BalanceService(BalanceDao balanceDao) {
		this.balanceDao = balanceDao;
	}

	public String getBalance(User user) {
		Balance balance = this.balanceDao.findOneByUserId(user.getId());
		if (balance == null) {
			return "0";
		}
		return String.format("%s %s", PriceUtils.toString(balance.getBalance()), balance.getCurrency());
	}

	public void updateBalance(User user, Long amount) {
		Balance balance = balanceDao.findOneByUserId(user.getId());
		balance.setBalance(balance.getBalance() + amount);
		balanceDao.save(balance);
	}
}
