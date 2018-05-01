package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Balance;

@Repository
@Transactional
public interface BalanceDao extends JpaRepository<Balance, Integer> {
	public Balance findOneByUserId(int userId);
}
