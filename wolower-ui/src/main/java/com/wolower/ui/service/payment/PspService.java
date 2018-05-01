package com.wolower.ui.service.payment;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.mastercard.merchant.checkout.model.PaymentData;

/* Payment Service Provider */
@Service
public class PspService {
	public PspServiceResponse authorize(PaymentData paymentData) {
		Random rand = new Random();
		int authorizationCode = rand.nextInt(899999) + 100000;
		return PspServiceResponse.builder().responseCode("00").authorizationCode(String.valueOf(authorizationCode))
				.build();
	}
}
