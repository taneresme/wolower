package com.wolower.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Transaction;

@Repository
@Transactional
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
	public Long countByUserId(int userId);

	@Query(value = "SELECT SUM(t.amount) as total FROM Transaction t where t.userId = :userId")
	public Long totalPurchaseByUserId(@Param("userId") int userId);

	public List<Transaction> findTop5ByUserId(int userId);
	
	public List<Transaction> findAllByUserId(int userId);
}
