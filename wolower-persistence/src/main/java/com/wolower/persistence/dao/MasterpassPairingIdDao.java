package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.MasterpassPairingId;

@Repository
@Transactional
public interface MasterpassPairingIdDao extends CrudRepository<MasterpassPairingId, Integer> {
	@Query(value = "UPDATE MasterpassPairingId t SET t.wasted = true WHERE t.userId = :userId", nativeQuery = true)
	public void wasteThemAllByUserId(@Param("userId") int userId);

	public MasterpassPairingId findOneByUserIdAndWastedOrderByIdDesc(int userId, Boolean wasted);
}
