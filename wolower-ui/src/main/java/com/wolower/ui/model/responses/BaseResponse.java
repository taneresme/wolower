package com.wolower.ui.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
	public String responseCode = "00";
	public String responseDesc = "SUCCESS";

	public BaseResponse() {

	}

	public BaseResponse(String responseCode, String responseDesc) {
		this.responseCode = responseCode;
		this.responseDesc = responseDesc;
	}
}
