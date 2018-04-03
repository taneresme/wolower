package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.SocialPost;

@Repository
@Transactional
public interface SocialPostDao extends CrudRepository<SocialPost, Integer> {
	
}
