package com.wolower.ui.model;

public class Dashboard {
	private String name;
	private int activeSales;
	private int totalTransactions;
	private long totalPurchase;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActiveSales() {
		return activeSales;
	}

	public void setActiveSales(int activeSales) {
		this.activeSales = activeSales;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public long getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(long totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

}
