package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.User;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
	public User findOneBySocialMediaAndSocialUserName(String socialMedia, String socialUserName);
	
	public User findOneBySocialUserName(String socialUserName);
	
	public User findOneBySocialUserId(String socialUserId);
}
