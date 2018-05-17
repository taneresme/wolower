package com.wolower.ui.service;

import com.wolower.persistence.dao.UserDao;
import com.wolower.persistence.model.Masterpass;
import com.wolower.persistence.model.User;
import com.wolower.ui.service.payment.MasterpassService;
import com.wolower.ui.social.SocialPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.twitter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceTest {
	@Mock
	private Twitter twitter;
	@Mock
	private UserDao userDao;
	@Mock
	private MasterpassService masterpassService;
	@InjectMocks
	@Spy
	private TwitterService service;

	@Test
	public void testCheckTweet(){
		User user = mock(User.class);
		Tweet tweet = mock(Tweet.class);
		Masterpass masterpass = mock(Masterpass.class);
		String fromUser = "fromUser";
		String text = "It is $100";
		Long inReplyToStatusId = 100000L;
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(text).when(tweet).getText();
		doReturn(inReplyToStatusId).when(tweet).getInReplyToStatusId();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);
		doReturn(masterpass).when(masterpassService).getMasterpass(user);

		Boolean result = service.checkTweet(tweet);

		assertEquals(true, result);
		verify(service, never()).reply(any(), any());
		verify(service, never()).reply(any(), any(), any());
	}

	@Test
	public void testCheckTweet_UserIsNull(){
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		TwitterProfile profile = mock(TwitterProfile.class);
		String fromUser = "fromUser";
		String screenName = "screenName";
		Long inReplyToStatusId = 100000L;
		doReturn(screenName).when(profile).getScreenName();
		doReturn(profile).when(tweet).getUser();
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(inReplyToStatusId).when(tweet).getInReplyToStatusId();
		doReturn(null).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);
		doReturn(socialPost).when(service).reply(any(), any());
		doReturn(tweet).when(service).reply(any(), any(), any());

		Boolean result = service.checkTweet(tweet);

		assertEquals(false, result);
	}

	@Test
	public void testCheckTweet_SameUser(){
		User user = mock(User.class);
		Tweet tweet = mock(Tweet.class);
		TwitterProfile profile = mock(TwitterProfile.class);
		String fromUser = "wolower_payment";
		long id = 10000L;
		String screenName = "screenName";
		doReturn(screenName).when(profile).getScreenName();
		doReturn(profile).when(tweet).getUser();
		doReturn(id).when(tweet).getId();
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);

		Boolean result = service.checkTweet(tweet);

		assertEquals(false, result);
		verify(service, never()).reply(eq(fromUser), eq(id), anyString());
	}

	@Test
	public void testCheckTweet_NoPrice(){
		User user = mock(User.class);
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		TwitterProfile profile = mock(TwitterProfile.class);
		String fromUser = "fromUser";
		String text = "There is no price!";
		String screenName = "screenName";
		Long id = 1000L;
		doReturn(id).when(tweet).getId();
		doReturn(screenName).when(profile).getScreenName();
		doReturn(profile).when(tweet).getUser();
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(text).when(tweet).getText();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);
		doReturn(socialPost).when(service).reply(any(), any());
		doReturn(tweet).when(service).reply(any(), any(), any());

		Boolean result = service.checkTweet(tweet);

		assertEquals(false, result);
		verify(service, times(1)).reply(eq(fromUser), eq(id), anyString());
	}

	@Test
	public void testCheckTweet_NoMasterpassPaired(){
		User user = mock(User.class);
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		TwitterProfile profile = mock(TwitterProfile.class);
		String fromUser = "fromUser";
		String text = "It is $100";
		String screenName = "screenName";
		Long inReplyToStatusId = 100000L;
		Long id = 1000L;
		doReturn(id).when(tweet).getId();
		doReturn(screenName).when(profile).getScreenName();
		doReturn(profile).when(tweet).getUser();
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(text).when(tweet).getText();
		doReturn(inReplyToStatusId).when(tweet).getInReplyToStatusId();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);
		doReturn(null).when(masterpassService).getMasterpass(user);
		doReturn(socialPost).when(service).reply(any(), any());
		doReturn(tweet).when(service).reply(any(), any(), any());

		Boolean result = service.checkTweet(tweet);

		assertEquals(false, result);
		verify(service, times(1)).reply(eq(fromUser), eq(id), anyString());
	}

	@Test
	public void testNewPost(){
		User user = mock(User.class);
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		TwitterProfile profile = mock(TwitterProfile.class);
		Date date = spy(Date.class);
		String fromUser = "fromUser";
		String text = "It is $100";
		String screenName = "screenName";
		Long inReplyToStatusId = 100000L;
		Long id = 1000L;
		Long userId = 2000L;
		doReturn(id).when(tweet).getId();
		doReturn(screenName).when(profile).getScreenName();
		doReturn(profile).when(tweet).getUser();
		doReturn(fromUser).when(tweet).getFromUser();
		doReturn(userId).when(tweet).getFromUserId();
		doReturn(text).when(tweet).getText();
		doReturn(date).when(tweet).getCreatedAt();
		doReturn(inReplyToStatusId).when(tweet).getInReplyToStatusId();
		doReturn(user).when(userDao).findOneBySocialMediaAndSocialUserName(service.SOCIAL_MEDIA, fromUser);
		doReturn(null).when(masterpassService).getMasterpass(user);
		doReturn(socialPost).when(service).reply(any(), any());
		doReturn(tweet).when(service).reply(any(), any(), any());

		SocialPost result = service.newPost(tweet);

		assertNotNull(result);
		assertEquals((Long)10000L, result.getAmount());
		assertEquals("USD", result.getCurrency());
		assertEquals(id, result.getPostId());
		assertEquals(text, result.getPostText());
		assertEquals(inReplyToStatusId, result.getRepliedPostId());
		assertEquals(userId, result.getSocialUserId());
		assertEquals(fromUser, result.getSocialUserName());
	}

	@Test
	public void testPost(){
		TimelineOperations timelineOperations = mock(TimelineOperations.class);
		String post = "post";
		doReturn(timelineOperations).when(twitter).timelineOperations();

		service.post(post);

		verify(timelineOperations, times(1)).updateStatus(post);
	}

	@Test
	public void testGetWoows_WithMentions(){
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		Long id = 1000L;
		SearchOperations searchOperations = mock(SearchOperations.class);
		SearchResults searchResults = mock(SearchResults.class);
		Long sinceId = 100000L;
		List<Tweet> mentions = new ArrayList<>();
		mentions.add(tweet);

		doReturn(searchOperations).when(twitter).searchOperations();
		doReturn(searchResults).when(searchOperations).search(any(), anyInt(), anyLong(), anyLong());
		doReturn(mentions).when(searchResults).getTweets();
		doReturn(true).when(service).checkTweet(any());
		doReturn(socialPost).when(service).newPost(any());
		doReturn(id).when(tweet).getId();

		List<SocialPost> result = service.getWoows(sinceId);

		assertEquals(mentions.size(), result.size());
		verify(searchOperations, times(1)).search(any(), anyInt(), anyLong(), anyLong());
		verify(searchResults, times(1)).getTweets();
	}

	@Test
	public void testGetWoows_WithNoMentions(){
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		SearchOperations searchOperations = mock(SearchOperations.class);
		SearchResults searchResults = mock(SearchResults.class);
		Long sinceId = 100000L;
		List<Tweet> mentions = new ArrayList<>();

		doReturn(searchOperations).when(twitter).searchOperations();
		doReturn(searchResults).when(searchOperations).search(any(), anyInt(), anyLong(), anyLong());
		doReturn(mentions).when(searchResults).getTweets();
		doReturn(true).when(service).checkTweet(any());
		doReturn(socialPost).when(service).newPost(any());

		List<SocialPost> result = service.getWoows(sinceId);

		assertEquals(mentions.size(), result.size());
		verify(service, never()).checkTweet(any());
		verify(service, never()).newPost(any());
		verify(tweet, never()).getId();
	}

	@Test
	public void testGetWoows_WithInnerException(){
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		Long id = 1000L;
		SearchOperations searchOperations = mock(SearchOperations.class);
		SearchResults searchResults = mock(SearchResults.class);
		Long sinceId = 100000L;
		List<Tweet> mentions = new ArrayList<>();
		mentions.add(tweet);

		doReturn(searchOperations).when(twitter).searchOperations();
		doReturn(searchResults).when(searchOperations).search(any(), anyInt(), anyLong(), anyLong());
		doReturn(mentions).when(searchResults).getTweets();
		doReturn(socialPost).when(service).newPost(any());
		doReturn(id).when(tweet).getId();

		List<SocialPost> result = service.getWoows(sinceId);

		assertEquals(0, result.size());
		verify(service, never()).newPost(any());
		verify(tweet, times(3)).getId();
	}

	@Test
	public void testGetWoows_WithOuterException(){
		Tweet tweet = mock(Tweet.class);
		SearchOperations searchOperations = mock(SearchOperations.class);
		Long sinceId = 100000L;

		doReturn(searchOperations).when(twitter).searchOperations();
		when(searchOperations.search(any(), anyInt(), anyLong(), anyLong())).thenThrow(Exception.class);

		List<SocialPost> result = service.getWoows(sinceId);

		assertEquals(0, result.size());
		verify(service, never()).newPost(any());
		verify(service, never()).checkTweet(any());
		verify(tweet, never()).getId();
	}

	@Test
	public void testReply_Tweet(){
		Tweet tweet = mock(Tweet.class);
		String fromUser = "fromUser";
		String reply = "reply";
		TimelineOperations timelineOperations = mock(TimelineOperations.class);
		Long postId = 100000L;
		doReturn(timelineOperations).when(twitter).timelineOperations();
		doReturn(tweet).when(timelineOperations).updateStatus(any(TweetData.class));

		Tweet result = service.reply(fromUser, postId, reply);

		assertEquals(tweet, result);
	}

	@Test
	public void testReply_SocialPost(){
		Tweet tweet = mock(Tweet.class);
		SocialPost socialPost = spy(SocialPost.class);
		String reply = "reply";
		String screenName = "screenName";
		Long id = 1000L;
		TimelineOperations timelineOperations = mock(TimelineOperations.class);
		doReturn(screenName).when(socialPost).getSocialUserName();
		doReturn(id).when(socialPost).getPostId();
		doReturn(timelineOperations).when(twitter).timelineOperations();
		doReturn(tweet).when(timelineOperations).updateStatus(any(TweetData.class));
		doReturn(socialPost).when(service).newPost(any());

		SocialPost result = service.reply(socialPost, reply);

		assertEquals(socialPost, result);
	}
}
