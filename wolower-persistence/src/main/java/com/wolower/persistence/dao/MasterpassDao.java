package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.Masterpass;

@Repository
@Transactional
public interface MasterpassDao extends CrudRepository<Masterpass, Integer> {
	public Masterpass findOneByUserIdAndEnabled(int userId, Boolean enabled);

	@Modifying
	@Query(value = "UPDATE Masterpass t SET t.enabled = :enabled WHERE t.userId = :userId")
	public void disableByUserId(@Param("userId") int userId, @Param("enabled") Boolean enabled);
}
