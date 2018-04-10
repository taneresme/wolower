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
	@Column(name = "masterpassPairingId")
	private int masterpassPairingId;

	@NotNull
	@Column(name = "authorizationCode")
	private String authorizationCode;

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

	@Column(name = "postId")
	private Long postId;

	@Column(name = "postDate")
	private LocalDateTime postDate;

	@Column(name = "postText")
	private String postText;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public Transaction() {
		this.timestamp = LocalDateTime.now();
	}
}
