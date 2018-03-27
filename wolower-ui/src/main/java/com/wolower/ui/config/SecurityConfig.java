package com.wolower.ui.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/index").permitAll()
				.antMatchers("/twitter/signup").permitAll()
				.antMatchers("/twitter/callback").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/**").authenticated()
			.and()
				.formLogin().loginPage("/index")
			.and()
				.logout().logoutSuccessUrl("/index?reason=logout")
			.and()
				.headers()
				.frameOptions().disable() // for h2
			.and()
				.requestCache()
				.requestCache(new NullRequestCache())
			.and()
				.sessionManagement()
				.invalidSessionUrl("/index?reason=invalidSession")
				.sessionAuthenticationErrorUrl("/index?reason=authenticationError")
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				/* protection against typical Session Fixation attacks by 
				 * configuring what happens to an existing session 
				 * when the user tries to authenticate again */
				.sessionFixation().migrateSession() 
			.and()
				.csrf().disable();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		/* This is essential to make sure that the Spring Security 
		 * session registry is notified when the session is destroyed. */
	    return new HttpSessionEventPublisher();
	}
}