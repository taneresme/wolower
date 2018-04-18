package com.wolower.persistence.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wolower.persistence.model.MasterpassExpressCheckout;

@Repository
@Transactional
public interface MasterpassExpressCheckoutDao extends CrudRepository<MasterpassExpressCheckout, Integer> {
}
