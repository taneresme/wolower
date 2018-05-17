package com.wolower.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mastercard.merchant.checkout.model.Pairing;
import com.mastercard.merchant.checkout.model.PreCheckoutData;
import com.wolower.ui.model.request.SavePrecheckoutRequest;
import com.wolower.ui.model.responses.BaseResponse;
import com.wolower.ui.service.MasterpassInitializationService;
import com.wolower.ui.service.SessionService;
import com.wolower.ui.util.ResponseCodes;

@Controller
public class MasterpassController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassController.class);

	@Autowired
	private MasterpassInitializationService masterpass;

	@Autowired
	private SessionService session;

	@GetMapping("/masterpass/callback")
	public String callback(Model model, @RequestParam(name = "oauth_token", required = false) String oauthToken,
			@RequestParam(name = "pairing_verifier", required = false) String pairingVerifier,
			@RequestParam(name = "pairing_token", required = false) String pairingToken,
			@RequestParam(name = "mpstatus", required = false) String mpstatus, HttpServletRequest request) {
		LOGGER.info("MASTERPASS called back... oauth_token: " + oauthToken);
		LOGGER.info("MASTERPASS called back... pairing_verifier: " + pairingVerifier);
		LOGGER.info("MASTERPASS called back... pairing_token: " + pairingToken);
		LOGGER.info("MASTERPASS called back... mpstatus: " + mpstatus);

		/* If user canceled authentication redirect to pairing page again */
		if (!"cancel".equals(mpstatus)) {
			Pairing pairing = masterpass.obtainAPairingId(pairingVerifier);
			masterpass.initMasterpass(pairing, oauthToken, pairingVerifier, pairingToken, mpstatus);
		}
		return "redirect:/profile?tab=payment-method";
	}

	@PostMapping("/masterpass/precheckout")
	public ResponseEntity<PreCheckoutData> precheckout(HttpServletRequest request) {
		try {
			PreCheckoutData response = masterpass.getPrecheckoutData();

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			LOGGER.error("MASTERPASS precheckout exception: " + ExceptionUtils.getStackTrace(e));
			return ResponseEntity.ok(null);
			// return ResponseEntity
			// .ok(new BaseResponse(ResponseCodes.PRECHECKOUT_FAILED, "We cannot retrieve
			// precheckout details!"));
		}
	}

	@PostMapping("/masterpass/save-precheckout")
	public ResponseEntity<BaseResponse> savePrecheckout(@RequestBody SavePrecheckoutRequest precheckout,
			HttpServletRequest request) {
		try {
			masterpass.saveCardAndAddress(precheckout);
			return ResponseEntity.ok(new BaseResponse());
		} catch (Exception e) {
			LOGGER.error("MASTERPASS savePrecheckout exception: " + ExceptionUtils.getStackTrace(e));
			return ResponseEntity.ok(
					new BaseResponse(ResponseCodes.PRECHECKOUT_SAVING_FAILED, "We cannot save precheckout details!"));
		}
	}

	@GetMapping("/masterpass/remove-pairing")
	public String removePairing(HttpServletRequest request) {
		LOGGER.info("MASTERPASS remove-pairing for user: " + session.user().getSocialUserName());
		masterpass.removePairing();
		return "redirect:/profile?tab=payment-method";
	}
}
