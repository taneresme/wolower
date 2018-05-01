package com.wolower.ui.model;

import com.wolower.persistence.enums.PairingStatuses;
import com.wolower.persistence.model.Masterpass;
import com.wolower.persistence.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
	private PairingStatuses masterpassPairingStatus;
	private Masterpass masterpass;
	private String masterpassCheckoutId;
	private User user;
	private String balance;
}
