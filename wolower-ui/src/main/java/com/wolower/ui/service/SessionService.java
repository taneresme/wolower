package com.wolower.ui.service;

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
				|| SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser");
	}

	public User user() {
		if (!authenticated())
			return null;
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
