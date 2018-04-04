package com.wolower.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wolower.persistence.model.SocialPost;
import com.wolower.ui.contract.SocialService;

@Service
public class SocialPostScheduler {
	private SocialService socialService;

	@Autowired
	public SocialPostScheduler(SocialService socialService) {
		this.socialService = socialService;
	}

	@SuppressWarnings("unused")
	@Scheduled(fixedDelay = 60000)
	public void readPosts() {
		/* gets last 20 mentions */
		List<SocialPost> mentions = socialService.getMentions();
		int a = 5;

	}
}
