package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.MasterpassPairingId;

@Repository
@Transactional
public interface MasterpassPairingIdDao extends CrudRepository<MasterpassPairingId, Integer> {
	@Modifying
	@Query(value = "UPDATE MasterpassPairingId t SET t.wasted = :wasted WHERE t.userId = :userId")
	public void wasteThemAllByUserId(@Param("userId") int userId, @Param("wasted") Boolean wasted);

	public MasterpassPairingId findOneByUserIdAndWastedOrderByIdDesc(int userId, Boolean wasted);
}
