package com.wolower.persistence.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Order;

@Repository
@Transactional
public interface OrderDao extends JpaRepository<Order, Integer> {
	public Order findOneByPostId(Long postId);

	// public boolean existsByPostId(Long postId);

	public Order findTopByOrderByPostIdDesc();

	public Long countByUserId(int userId);

	public List<Order> findAllByUserId(int userId);

	public List<Order> findAllByPaid(Boolean paid);
}
