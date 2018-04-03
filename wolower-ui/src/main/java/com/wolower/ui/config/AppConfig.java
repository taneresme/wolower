package com.wolower.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.wolower.ui.service.SocialPostScheduler;
import com.wolower.ui.service.TwitterService;

@Configuration
@ComponentScan
@EnableScheduling
public class AppConfig {

//	@Bean
//	public SocialPostScheduler task() {
//		return new SocialPostScheduler(twitterService());
//	}
//	
//	@Bean
//	public TwitterService twitterService() {
//		return new TwitterService();
//	}
}
