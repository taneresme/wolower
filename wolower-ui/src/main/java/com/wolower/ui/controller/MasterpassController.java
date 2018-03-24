package com.wolower.ui.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mastercard.sdk.core.MasterCardApiConfig;

@Controller
public class MasterpassController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterpassController.class);

	@GetMapping("/masterpass/pairing")
	public String pairing() {
		return "/views/masterpass/pairing";
	}

	@GetMapping("/masterpass/callback")
	public String callback(@RequestParam(name = "oauth_token", required = false) String oauthToken,
			@RequestParam(name = "pairing_verifier", required = false) String pairingVerifier,
			@RequestParam(name = "pairing_token", required = false) String pairingToken,
			@RequestParam(name = "mpstatus", required = false) String mpstatus) {

		LOGGER.info("MASTERPASS called back... oauth_token: %s", oauthToken);
		LOGGER.info("MASTERPASS called back... pairing_verifier: %s", pairingVerifier);
		LOGGER.info("MASTERPASS called back... pairing_token: %s", pairingToken);
		LOGGER.info("MASTERPASS called back... mpstatus: %s", mpstatus);

		// MasterCardApiConfig.setSandBox(false); // to use production environment else
		MasterCardApiConfig.setSandBox(true); // to use sandbox environment
		MasterCardApiConfig.setConsumerKey("YOUR_CONSUMER_KEY");
		// MasterCardApiConfig.setPrivateKey(YOUR_PRIVATE_KEY);

		// Set up Proxy (optional)
		MasterCardApiConfig.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)));

		// You can set a specific TLS version to use for the Masterpass SDK connections
		// as follows:

		MasterCardApiConfig.setTlsProtocol("TLSv1.2");

		// All versions of TLS offered by the okhttp client are supported e.g.
		// "TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1.0". If no TLS version is supplied
		// then the SDK may use
		// any TLS version from 1.3 to 1.0

		/* If user canceled authentication redirect to pairing page again */
		if ("cancel".equals(mpstatus))
			return "/views/masterpass/pairing";
		else
			return "/views/masterpass/pairing";
	}
}
