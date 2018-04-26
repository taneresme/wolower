package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import com.wolower.persistence.enums.PairingStatuses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tMasterpass")
public class Masterpass {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "userId")
	private int userId;

	@NotNull
	@Column(name = "oauthToken")
	private String oauthToken;

	@Column(name = "pairingVerifier")
	private String pairingVerifier;

	@Column(name = "pairingToken")
	private String pairingToken;

	@NotNull
	@Column(name = "mpStatus")
	private String mpStatus;

	@Column(name = "recipientName")
	private String recipientName;

	@Column(name = "recipientPhone")
	private String recipientPhone;

	@Column(name = "defaultCardId")
	private String defaultCardId;

	@Column(name = "defaultCardLast4")
	private String defaultCardLast4;

	@Column(name = "defaultShippingAddressId")
	private String defaultShippingAddressId;

	@Column(name = "defaultShippingAddress")
	private String defaultShippingAddresss;

	@NotNull
	@Column(name = "pairingStatus")
	private PairingStatuses pairingStatus;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@NotNull
	@ColumnDefault(value = "true")
	@Column(name = "enabled")
	private Boolean enabled;

	public Masterpass() {
		this.timestamp = LocalDateTime.now();
		this.enabled = true;
		this.pairingStatus = PairingStatuses.NONE;
	}
}
