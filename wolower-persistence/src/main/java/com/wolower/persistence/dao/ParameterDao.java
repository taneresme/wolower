package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Parameter;

@Repository
@Transactional
public interface ParameterDao extends JpaRepository<Parameter, Integer> {
	public Parameter findOneByParameterKey(String parameterKey);
}
