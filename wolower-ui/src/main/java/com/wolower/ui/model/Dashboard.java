package com.wolower.ui.model;

import java.time.LocalDateTime;
import java.util.List;

import com.wolower.persistence.model.Transaction;
import com.wolower.ui.util.PriceUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard {
	private String name;
	private Long activeSales;
	private Long totalOrders;
	private Double totalPurchase;
	private List<TransactionModel> transactions;
	
	@Getter
	@Setter
	public static class TransactionModel {
		private LocalDateTime timestamp;
		private String socialMedia;
		private String amount;
		private String authorizationCode;
		private String responseCode;

		public TransactionModel(Transaction transaction) {
			this.timestamp = transaction.getTimestamp();
			this.socialMedia = transaction.getSocialMedia();
			this.amount = String.format("%s %s", PriceUtils.toString(transaction.getAmount()),
					transaction.getCurrency());
			this.authorizationCode = transaction.getAuthorizationCode();
			this.responseCode = transaction.getResponseCode();
		}
	}
}
