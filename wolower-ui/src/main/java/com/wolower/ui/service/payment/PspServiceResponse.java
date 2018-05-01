package com.wolower.ui.service.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PspServiceResponse {
	/* Field 39 */
	private String responseCode;
	private String authorizationCode;

	public PspServiceResponse(String responseCode, String authorizationCode) {
		super();
		this.responseCode = responseCode;
		this.authorizationCode = authorizationCode;
	}

}
