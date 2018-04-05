package com.wolower.ui.service;

import org.springframework.stereotype.Service;

import com.wolower.persistence.dao.SocialPostDao;
import com.wolower.ui.contract.SocialService;

@Service
public class TwitterScheduler extends SocialPostScheduler {

	public TwitterScheduler(SocialService socialService, SocialPostDao socialPostDao) {
		super(socialService, socialPostDao);
	}

}
