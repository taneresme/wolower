package com.wolower.ui.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.wolower.persistence.dao.SocialPostDao;
import com.wolower.persistence.enums.PostTypes;
import com.wolower.persistence.model.SocialPost;
import com.wolower.ui.contract.SocialService;

public class TwitterPostScheduler {
	private SocialService socialService;
	private SocialPostDao socialPostDao;

	@Autowired
	public TwitterPostScheduler(SocialService socialService, SocialPostDao socialPostDao) {
		this.socialService = socialService;
		this.socialPostDao = socialPostDao;
	}

	@SuppressWarnings("unused")
	@Scheduled(fixedDelay = 60000)
	public void readPosts() {
		/* gets last 20 mentions */
		List<SocialPost> posts = socialService.getMentions();
		List<SocialPost> postsToReply = new ArrayList<>();
		for (SocialPost post : posts) {
			if (socialPostDao.findOneByPostId(String.valueOf(post.getPostId())) != null) {
				postsToReply.add(post);
			}
		}

		/* We update the old ones and save the new ones. */
		socialPostDao.save(posts);
		
		for (SocialPost post : postsToReply) {
			if (post.getPostType() == PostTypes.BUYING) {
				socialService.reply(post, "Now, we have your order :)");
			}
			else if (post.getPostType() == PostTypes.SELLING) {
				socialService.reply(post, "It is on me :)");
			}
		}
	}
}
