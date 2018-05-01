package com.wolower.ui.model;

import java.time.LocalDateTime;
import java.util.List;

import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.Transaction;
import com.wolower.ui.util.PriceUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
	private List<OrderModel> orders;
	private List<ProductModel> products;
	private List<TransactionModel> transactions;

	@Getter
	@Setter
	public static class ProductModel {
		private LocalDateTime timestamp;
		private String socialMedia;
		private String price;
		private String sold;
		private String postText;
		private String link;

		public ProductModel(Product product) {
			this.timestamp = product.getTimestamp();
			this.socialMedia = product.getSocialMedia();
			this.price = String.format("%s %s", PriceUtils.toString(product.getPrice()), product.getCurrency());
			this.sold = product.getSold() ? "sold" : "not sold";
			this.postText = product.getPostText();
			this.link = String.format("https://twitter.com/%s/status/%s", product.getSocialUserName(),
					product.getPostId());
		}
	}

	@Getter
	@Setter
	public static class OrderModel {
		private LocalDateTime timestamp;
		private String socialMedia;
		private String price;
		private String postText;
		private String link;

		public OrderModel(Order order) {
			this.timestamp = order.getTimestamp();
			this.socialMedia = order.getSocialMedia();
			this.price = String.format("%s %s", PriceUtils.toString(order.getPrice()), order.getCurrency());
			this.postText = order.getPostText();
			this.link = String.format("https://twitter.com/%s/status/%s", order.getSocialUserName(), order.getPostId());
		}
	}

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