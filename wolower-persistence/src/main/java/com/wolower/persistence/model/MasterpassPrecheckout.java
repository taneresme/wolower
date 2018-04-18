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
@Table(name = "tMasterpassPrecheckouts")
public class MasterpassPrecheckout {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "userId")
	private int userId;

	@NotNull
	@Column(name = "masterpassId")
	private int masterpassId;

	@NotNull
	@Column(name = "precheckoutTransactionId")
	private String precheckoutTransactionId;

	@NotNull
	@Column(name = "consumerWalletId")
	private String consumerWalletId;

	@NotNull
	@Column(name = "walletName")
	private String walletName;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public MasterpassPrecheckout() {
		this.timestamp = LocalDateTime.now();
	}
}
