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
	private List<Order> orders;
	private List<ProductModel> products;
	private List<Transaction> transactions;

	@Getter
	@Setter
	public static class ProductModel {
		private LocalDateTime timestamp;
		private String socialMedia;
		private String price;
		private String sold;

		public ProductModel(Product product) {
			this.timestamp = product.getTimestamp();
			this.socialMedia = product.getSocialMedia();
			this.price = String.format("%s %s", PriceUtils.toString(product.getPrice()), product.getCurrency());
			this.sold = product.getSold() ? "sold" : "not sold";
			this.timestamp = product.getTimestamp();
		}
	}
}