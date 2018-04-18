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
@Table(name = "tMasterpassExpressCheckouts")
public class MasterpassExpressCheckout {
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
	@Column(name = "walletId")
	private String walletId;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public MasterpassExpressCheckout() {
		this.timestamp = LocalDateTime.now();
	}
}
