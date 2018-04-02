package com.wolower.ui.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wolower.persistence.model.User;

@Service
public class SessionService {
	public void setSession(User user) {
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, null));
	}

	public Boolean authenticated() {
		return !(SecurityContextHolder.getContext().getAuthentication() == null);
	}

	public String userDisplayName() {
		if (!authenticated())
			return "";
		User user = user();
		return user.getDisplayName();
	}

	public User user() {
		if (!authenticated())
			return null;
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
