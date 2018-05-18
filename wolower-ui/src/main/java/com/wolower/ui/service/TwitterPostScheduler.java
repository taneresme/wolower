package com.wolower.ui.service;

import static com.wolower.ui.util.Converter.toOrder;
import static com.wolower.ui.util.Converter.toProduct;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ParameterDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.enums.ParameterKeys;
import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Parameter;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.User;
import com.wolower.ui.contract.SocialService;
import com.wolower.ui.social.SocialPost;
import com.wolower.ui.util.PriceUtils;

@Service
public class TwitterPostScheduler {
	private Logger logger = LoggerFactory.getLogger(TwitterPostScheduler.class);

	private SocialService socialService;
	private OrderDao orderDao;
	private ProductDao productDao;
	private UserDao userDao;
	private ParameterDao parameterDao;

	@Autowired
	public TwitterPostScheduler(SocialService socialService, OrderDao orderDao, ProductDao productDao, UserDao userDao,
			ParameterDao parameterDao) {
		this.socialService = socialService;
		this.orderDao = orderDao;
		this.productDao = productDao;
		this.userDao = userDao;
		this.parameterDao = parameterDao;
	}

	@Scheduled(fixedDelay = 60000)
	public void readPosts() {
		/* Get last post ID processed by the wolower */
		Parameter lastPostIdParameter = parameterDao
				.findOneByParameterKey(ParameterKeys.TWITTER_LAST_PROCESSED_POST_ID.toString());
		if (lastPostIdParameter == null) {
			lastPostIdParameter = new Parameter(ParameterKeys.TWITTER_LAST_PROCESSED_POST_ID.toString(), "0");
		}
		Long lastPostId = Long.valueOf(lastPostIdParameter.getParameterValue());

		/* gets last 30 mentions */
		List<SocialPost> posts = socialService.getWoows(lastPostId);
		if (posts.size() > 0) {
			List<Order> orders = new ArrayList<>();
			List<SocialPost> ordersToReply = new ArrayList<>();
			List<Product> products = new ArrayList<>();
			List<SocialPost> productsToReply = new ArrayList<>();

			/* Process the posts fetched */
			for (SocialPost post : posts) {
				User user = userDao.findOneBySocialMediaAndSocialUserName(post.getSocialMedia(),
						post.getSocialUserName());
				if (user != null) {
					if (post.getIsReply()
							&& orderDao.findOneByPostId(post.getPostId()) == null
							&& productDao.findOneByPostId(post.getRepliedPostId()) != null) {
						/* There is no order with this post ID */
						ordersToReply.add(post);
						int productId = productDao.findOneByPostId(post.getRepliedPostId()).getId();
						orders.add(toOrder(post, productId, user.getId()));
					} else if (!post.getIsReply() && productDao.findOneByPostId(post.getPostId()) == null) {
						/* There is no product with this post ID */
						productsToReply.add(post);
						products.add(toProduct(post, user.getId()));
					}
				} else {
					logger.debug("User is NULL for SocialMedia: %s and SocialUserName: %s", post.getSocialMedia(),
							post.getSocialUserName());
				}
			}

			/* We update the old ones and save the new ones. */
			orderDao.save(orders);
			productDao.save(products);

			/* Reply the posts */
			for (SocialPost post : ordersToReply) {
				socialService.reply(post, "Now, we have your order :)");
			}
			for (SocialPost post : productsToReply) {
				socialService.reply(post, String.format("I got it and store it with %s %s",
						PriceUtils.toString(post.getAmount()), post.getCurrency()));
			}
		} else {
			logger.info("No tweet fetched!");
		}
		
		/* Save last processed post ID */
		lastPostIdParameter.setParameterValue(socialService.getLastPostId().toString());
		parameterDao.save(lastPostIdParameter);
	}
}
