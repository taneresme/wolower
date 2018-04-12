package com.wolower.ui.model;

import java.util.List;

import com.wolower.persistence.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard {
	private String name;
	private Long activeSales;
	private Long totalTransactions;
	private Long totalPurchase;
	private List<Transaction> transactions;
}
