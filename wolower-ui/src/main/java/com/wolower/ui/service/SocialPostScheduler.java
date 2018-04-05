package com.wolower.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.SocialPostDao;
import com.wolower.persistence.model.SocialPost;
import com.wolower.ui.contract.SocialService;

public class SocialPostScheduler {
	private SocialService socialService;
	private SocialPostDao socialPostDao;

	@Autowired
	public SocialPostScheduler(SocialService socialService, SocialPostDao socialPostDao) {
		this.socialService = socialService;
		this.socialPostDao = socialPostDao;
	}

	@SuppressWarnings("unused")
	@Scheduled(fixedDelay = 60000)
	public void readPosts() {
		/* gets last 20 mentions */
		List<SocialPost> posts = socialService.getMentions();
		socialPostDao.save(posts);
	}
}
