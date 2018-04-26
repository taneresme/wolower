package com.wolower.ui.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.wolower.persistence.model.User;

@SessionScope
@Service
public class SessionService {
	public void setSession(User user) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, null));
	}

	public Boolean authenticated() {
		return !(SecurityContextHolder.getContext().getAuthentication() == null
				|| "anonymousUser".equals((SecurityContextHolder.getContext().getAuthentication().getPrincipal())));
	}

	public User user() {
		if (!authenticated())
			return null;
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public void logout() {
		SecurityContextHolder.clearContext();
	}
	
	public Boolean getFirstTime() {
		return user().getTimestamp().plusDays((long)1).isAfter(LocalDateTime.now());
	}
}
