package com.wolower.ui.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePrecheckoutRequest {
	private String recipientName;
	private String recipientPhone;
	private String cardId;
	private String cardLast4;
	private String addressId;
	private String address;
}
