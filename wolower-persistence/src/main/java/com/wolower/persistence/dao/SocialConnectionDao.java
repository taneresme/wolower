package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.SocialConnection;

@Repository
@Transactional
public interface SocialConnectionDao extends CrudRepository<SocialConnection, Integer> {
	public SocialConnection findOneBySocialMediaAndSocialUserId(String socialMedia, String socialUserId);
}
