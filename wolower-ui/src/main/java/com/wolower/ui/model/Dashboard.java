package com.wolower.ui.model;

public class Dashboard {
	private String name;
	private Long activeSales;
	private Long totalTransactions;
	private Long totalPurchase;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getActiveSales() {
		return activeSales;
	}

	public void setActiveSales(Long activeSales) {
		this.activeSales = activeSales;
	}

	public Long getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(Long totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public Long getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(Long totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

}
