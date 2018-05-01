package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tTransactions")
public class Transaction {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "userId")
	private int userId;

	@NotNull
	@Column(name = "orderId")
	private int orderId;

	@NotNull
	@Column(name = "masterpassId")
	private int masterpassId;

	@NotNull
	@Column(name = "pairingId")
	private String pairingId;

	@NotNull
	@Column(name = "transactionId")
	private String transactionId;

	@NotNull
	@Column(name = "authorizationCode")
	private String authorizationCode;

	@NotNull
	@Column(name = "responseCode")
	private String responseCode;

	@NotNull
	@Column(name = "socialPostId")
	private String socialMedia;

	@NotNull
	@Column(name = "socialUserId")
	private Long socialUserId;

	@NotNull
	@Column(name = "socialUserName")
	private String socialUserName;

	@NotNull
	@Column(name = "amount")
	private Long amount;

	@NotNull
	@Column(name = "currency")
	private String currency;

	@NotNull
	@Column(name = "PAN")
	private String PAN;

	@NotNull
	@Column(name = "shippingAddress")
	private String shippingAddress;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public Transaction() {
		this.timestamp = LocalDateTime.now();
	}
}
