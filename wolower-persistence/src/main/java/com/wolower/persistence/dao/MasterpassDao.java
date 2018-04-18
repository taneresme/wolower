package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Masterpass;

@Repository
@Transactional
public interface MasterpassDao extends CrudRepository<Masterpass, Integer> {
	public Masterpass findOneByUserIdAndEnabled(int userId, Boolean enabled);
}
