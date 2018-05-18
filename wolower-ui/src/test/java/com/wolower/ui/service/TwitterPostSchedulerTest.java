package com.wolower.ui.service;

import com.wolower.persistence.dao.OrderDao;
import com.wolower.persistence.dao.ParameterDao;
import com.wolower.persistence.dao.ProductDao;
import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.Order;
import com.wolower.persistence.model.Parameter;
import com.wolower.persistence.model.Product;
import com.wolower.persistence.model.User;
import com.wolower.ui.contract.SocialService;
import com.wolower.ui.social.SocialPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterPostSchedulerTest {

	@Mock
	private SocialService socialService;
	@Mock
	private OrderDao orderDao;
	@Mock
	private ProductDao productDao;
	@Mock
	private UserDao userDao;
	@Mock
	private ParameterDao parameterDao;
	@InjectMocks
	@Spy
	private TwitterPostScheduler service;

	@Test
	public void testReadPostsWithNoWoow(){
		Long id = 1000L;
		Parameter parameter = mock(Parameter.class);
		String parameterValue = "10";
		List<SocialPost> posts = new ArrayList<>();

		doReturn(parameterValue).when(parameter).getParameterValue();
		doReturn(parameter).when(parameterDao).findOneByParameterKey(any());
		doReturn(posts).when(socialService).getWoows(any());
		doReturn(id).when(socialService).getLastPostId();

		service.readPosts();

		verify(parameterDao, times(1)).findOneByParameterKey(any());
		verify(parameterDao, times(1)).save(any(Parameter.class));
		verify(userDao, never()).findOneBySocialMediaAndSocialUserName(any(), any());
		verify(orderDao, never()).findOneByPostId(any());
		verify(productDao, never()).findOneByPostId(any());
		verify(productDao, never()).save(anyList());
		verify(orderDao, never()).save(anyList());
		verify(socialService, never()).reply(any(), any());
	}

	@Test
	public void testReadPostsWithNoWoowAndNullParameter(){
		Long id = 1000L;
		List<SocialPost> posts = new ArrayList<>();

		doReturn(null).when(parameterDao).findOneByParameterKey(any());
		doReturn(posts).when(socialService).getWoows(any());
		doReturn(id).when(socialService).getLastPostId();

		service.readPosts();

		verify(parameterDao, times(1)).findOneByParameterKey(any());
		verify(parameterDao, times(1)).save(any(Parameter.class));
		verify(userDao, never()).findOneBySocialMediaAndSocialUserName(any(), any());
		verify(orderDao, never()).findOneByPostId(any());
		verify(productDao, never()).findOneByPostId(any());
		verify(productDao, never()).save(anyList());
		verify(orderDao, never()).save(anyList());
		verify(socialService, never()).reply(any(), any());
	}

	@Test
	public void testReadPostsWithWoowAndOrder(){
		User user = mock(User.class);
		SocialPost socialPost = spy(SocialPost.class);
		Long id = 1000L;
		int userId = 2000;
		List<SocialPost> posts = new ArrayList<>();
		posts.add(socialPost);
		Product product = mock(Product.class);
		int productId = 1;

		doReturn(true).when(socialPost).getIsReply();
		doReturn(null).when(parameterDao).findOneByParameterKey(any());
		doReturn(posts).when(socialService).getWoows(any());
		doReturn(id).when(socialService).getLastPostId();
		doReturn(userId).when(user).getId();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(any(), any());
		doReturn(null).when(orderDao).findOneByPostId(any());
		doReturn(product).when(productDao).findOneByPostId(any());
		doReturn(productId).when(product).getId();

		service.readPosts();

		verify(parameterDao, times(1)).findOneByParameterKey(any());
		verify(parameterDao, times(1)).save(any(Parameter.class));
		verify(userDao, times(1)).findOneBySocialMediaAndSocialUserName(any(), any());
		verify(orderDao, times(1)).findOneByPostId(any());
		verify(productDao, times(2)).findOneByPostId(any());
		verify(productDao, times(1)).save(anyList());
		verify(orderDao, times(1)).save(anyList());
		verify(socialService, times(1)).reply(any(), any());
	}

	@Test
	public void testReadPostsWithWoowAndProduct(){
		User user = mock(User.class);
		SocialPost socialPost = spy(SocialPost.class);
		Long id = 1000L;
		int userId = 2000;
		List<SocialPost> posts = new ArrayList<>();
		posts.add(socialPost);
		Product product = mock(Product.class);
		int productId = 1;
		Order order = mock(Order.class);
		Long amount = 10000L;
		String currency = "USD";

		doReturn(false).when(socialPost).getIsReply();
		doReturn(amount).when(socialPost).getAmount();
		doReturn(currency).when(socialPost).getCurrency();
		doReturn(null).when(parameterDao).findOneByParameterKey(any());
		doReturn(posts).when(socialService).getWoows(any());
		doReturn(id).when(socialService).getLastPostId();
		doReturn(userId).when(user).getId();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(any(), any());
		doReturn(order).when(orderDao).findOneByPostId(any());
		doReturn(null).when(productDao).findOneByPostId(any());
		doReturn(productId).when(product).getId();

		service.readPosts();

		verify(parameterDao, times(1)).findOneByParameterKey(any());
		verify(parameterDao, times(1)).save(any(Parameter.class));
		verify(userDao, times(1)).findOneBySocialMediaAndSocialUserName(any(), any());
		verify(orderDao, never()).findOneByPostId(any());
		verify(productDao).findOneByPostId(any());
		verify(productDao, times(1)).save(anyList());
		verify(orderDao, times(1)).save(anyList());
		verify(socialService, times(1)).reply(any(), any());
	}
}
