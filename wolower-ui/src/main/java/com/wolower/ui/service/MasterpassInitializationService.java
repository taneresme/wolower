package com.wolower.ui.service;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.mastercard.merchant.checkout.PairingIdApi;
import com.mastercard.merchant.checkout.model.Pairing;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.mastercard.sdk.core.exceptions.SDKErrorResponseException;
import com.mastercard.sdk.core.models.Errors;
import com.mastercard.sdk.core.models.Error;
import com.mastercard.sdk.core.util.QueryParams;
import com.wolower.persistence.dao.MasterpassDao;
import com.wolower.persistence.enums.PairingStatuses;
import com.wolower.persistence.model.Masterpass;
import com.wolower.ui.config.MasterpassConfig;
import com.wolower.ui.service.payment.MasterpassService;

/* This service is used when first pairing with Masterpass
 * Simply, its used from the interface of the wolower */
@SessionScope
@Service
public class MasterpassInitializationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassInitializationService.class);

	private MasterpassConfig config;
	private MasterpassService masterpassService;
	private SessionService session;
	private MasterpassDao masterpassDao;
	/* Each of the users will have only one masterpass record */
	private Masterpass masterpass;

	@Autowired
	public MasterpassInitializationService(MasterpassConfig config, MasterpassService masterpassService,
			SessionService session, MasterpassDao masterpassDao) {
		this.config = config;
		this.masterpassService = masterpassService;
		this.session = session;
		this.masterpassDao = masterpassDao;
	}

	public String getCheckoutId() {
		return config.getCheckoutId();
	}

	public void saveMasterpass(Pairing pairing, String oauthToken, String pairingVerifier, String pairingToken,
			String mpstatus, PairingStatuses status) {
		this.masterpass = getMasterpass() == null ? new Masterpass() : masterpass;
		this.masterpass.setUserId(session.user().getId());
		this.masterpass.setMpStatus(mpstatus);
		this.masterpass.setOauthToken(oauthToken);
		this.masterpass.setPairingToken(pairingToken);
		this.masterpass.setPairingVerifier(pairingVerifier);
		this.masterpass.setPairingStatus(status);

		this.masterpass = masterpassDao.save(masterpass);
	}

	public Masterpass getMasterpass() {
		if (this.masterpass == null) {
			this.masterpass = masterpassDao.findOneByUserIdAndEnabled(session.user().getId(), true);
		}
		return this.masterpass;
	}

	public PairingStatuses masterpassPairingStatus() {
		return getMasterpass() == null ? PairingStatuses.NONE : masterpass.getPairingStatus();
	}

	/* Obtain pairing id when initializing Masterpass */
	public Pairing obtainAPairingId(String pairingVerifier) {
		try {
			QueryParams queryParams = new QueryParams().add("pairingTransactionId", pairingVerifier).add("userId",
					"mpassUserId");

			Pairing pairingId = PairingIdApi.show(queryParams);
			return pairingId;
		} catch (Exception ex) {
			MasterpassService.masterpassExcetion(ex, LOGGER);
			throw ex;
		}
	}

	public PreCheckoutData getPrecheckoutData() {
		return masterpassService.getPrecheckoutData(session.user());
	}
}
