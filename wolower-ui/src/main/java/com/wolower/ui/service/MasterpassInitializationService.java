package com.wolower.ui.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.mastercard.merchant.checkout.PairingIdApi;
import com.mastercard.merchant.checkout.model.Pairing;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.mastercard.sdk.core.util.QueryParams;
import com.wolower.persistence.dao.MasterpassDao;
import com.wolower.persistence.enums.PairingIdSources;
import com.wolower.persistence.enums.PairingStatuses;
import com.wolower.persistence.model.Masterpass;
import com.wolower.ui.config.MasterpassConfig;
import com.wolower.ui.model.request.SavePrecheckoutRequest;
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

	void setMasterpass(Masterpass masterpass){
		this.masterpass = masterpass;
	}

	public String getCheckoutId() {
		return config.getCheckoutId();
	}

	public void initMasterpass(Pairing pairing, String oauthToken, String pairingVerifier, String pairingToken,
			String mpstatus) {
		this.masterpass = getMasterpass() == null ? new Masterpass() : getMasterpass();
		this.masterpass.setUserId(session.user().getId());
		this.masterpass.setMpStatus(mpstatus);
		this.masterpass.setOauthToken(oauthToken);
		this.masterpass.setPairingToken(pairingToken);
		this.masterpass.setPairingVerifier(pairingVerifier);
		this.masterpass.setPairingStatus(PairingStatuses.NONE);
		this.masterpass = masterpassDao.save(this.masterpass);

		this.masterpassService.savePairingId(pairing.getPairingId(), PairingIdSources.INITIALIZATION, session.user(),
				this.masterpass);

		/* If we save pairing id successfully */
		this.masterpass.setPairingStatus(PairingStatuses.PRECHECKOUT_PENDING);
		this.masterpass = masterpassDao.save(this.masterpass);
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
					String.valueOf(session.user().getId()));

			Pairing pairingId = PairingIdApi.show(queryParams);
			return pairingId;
		} catch (Exception ex) {
			MasterpassService.masterpassException(ex, LOGGER);
			throw ex;
		}
	}

	public PreCheckoutData getPrecheckoutData() {
		return masterpassService.getPrecheckoutData(session.user());
	}

	public void saveCardAndAddress(SavePrecheckoutRequest precheckout) {
		this.masterpass.setRecipientName(precheckout.getRecipientName());
		this.masterpass.setRecipientPhone(precheckout.getRecipientPhone());
		this.masterpass.setDefaultCardLast4(precheckout.getCardLast4());
		this.masterpass.setDefaultCardId(precheckout.getCardId());
		this.masterpass.setDefaultShippingAddresss(precheckout.getAddress());
		this.masterpass.setDefaultShippingAddressId(precheckout.getAddressId());
		this.masterpass.setPairingStatus(PairingStatuses.COMPLETED);
		this.masterpassDao.save(this.masterpass);
	}

	public void removePairing() {
		this.masterpassDao.disableByUserId(session.user().getId(), false);
		this.masterpass = null;
	}
}
