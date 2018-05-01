package com.wolower.ui.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mastercard.sdk.core.MasterCardApiConfig;

@Configuration
public class MasterpassConfig {
	private Logger logger = LoggerFactory.getLogger(MasterpassConfig.class);

	@Value("${wolower.masterpass.checkoutId}")
	private String checkoutId;
	@Value("${wolower.masterpass.consumerKey}")
	private String consumerKey;
	@Value("${wolower.masterpass.isSandbox}")
	private String isSandbox;
	@Value("${wolower.masterpass.keystorePath}")
	private String keystorePath;
	@Value("${wolower.masterpass.keystorePassword}")
	private String keystorePassword;

	public String getCheckoutId() {
		return checkoutId;
	}

	@PostConstruct
	public void configure() {
		PrivateKey privateKey = null;
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream readStream = new FileInputStream(this.keystorePath);
			ks.load(readStream, this.keystorePassword.toCharArray());
			Enumeration<String> aliases = ks.aliases();
			String keyAlias = "defaultSandboxKey";

			while (aliases.hasMoreElements()) {
				keyAlias = (String) aliases.nextElement();
			}

			privateKey = (PrivateKey) ks.getKey(keyAlias, this.keystorePassword.toCharArray());

			MasterCardApiConfig.setSandBox(Boolean.valueOf(this.isSandbox)); // to use sandbox environment
			MasterCardApiConfig.setConsumerKey(this.consumerKey);
			MasterCardApiConfig.setPrivateKey(privateKey);

			// Set up Proxy (optional)
			// MasterCardApiConfig.setProxy(new Proxy(Proxy.Type.HTTP, new
			// InetSocketAddress("127.0.0.1", 8888)));

			// You can set a specific TLS version to use for the Masterpass SDK connections
			// as follows:

			MasterCardApiConfig.setTlsProtocol("TLSv1.2");

			// All versions of TLS offered by the okhttp client are supported e.g.
			// "TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1.0". If no TLS version is supplied
			// then the SDK may use
			// any TLS version from 1.3 to 1.0
		} catch (Exception ex) {
			logger.error(ExceptionUtils.getStackTrace(ex));
		}
	}
}
