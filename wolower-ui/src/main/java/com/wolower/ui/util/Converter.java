package com.wolower.ui.util;

import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Product;
import com.wolower.ui.social.SocialPost;

public class Converter {
	public static Order toOrder(SocialPost post, int productId, int userId) {
		Order order = new Order();
		order.setCurrency(post.getCurrency());
		order.setPostDate(post.getPostDate());
		order.setPostId(post.getPostId());
		order.setPostText(post.getPostText());
		order.setPrice(post.getAmount());
		order.setProductId(productId);
		order.setSocialMedia(post.getSocialMedia());
		order.setSocialUserId(post.getSocialUserId());
		order.setSocialUserName(post.getSocialUserName());
		order.setUserId(userId);
		return order;
	}

	public static Product toProduct(SocialPost post, int userId) {
		Product product = new Product();
		product.setCurrency(post.getCurrency());
		product.setPostDate(post.getPostDate());
		product.setPostId(post.getPostId());
		product.setPostText(post.getPostText());
		product.setPrice(post.getAmount());
		product.setSocialMedia(post.getSocialMedia());
		product.setSocialUserId(post.getSocialUserId());
		product.setSocialUserName(post.getSocialUserName());
		product.setUserId(userId);
		return product;
	}
}
