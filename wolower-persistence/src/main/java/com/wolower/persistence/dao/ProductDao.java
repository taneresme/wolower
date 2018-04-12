package com.wolower.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Product;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Integer> {
	public Product findOneByPostId(Long postId);

	// public boolean existsByPostId(Long postId);

	public Product findTopByOrderByPostIdDesc();

	public Long countByUserIdAndSold(int userId, Boolean sold);

	public List<Product> findAllBySoldAndUserId(Boolean sold, int userId);
}
