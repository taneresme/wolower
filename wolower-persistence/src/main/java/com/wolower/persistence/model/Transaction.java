package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getMasterpassId() {
		return masterpassId;
	}

	public void setMasterpassId(int masterpassId) {
		this.masterpassId = masterpassId;
	}

	public int getMasterpassPairingId() {
		return masterpassPairingId;
	}

	public void setMasterpassPairingId(int masterpassPairingId) {
		this.masterpassPairingId = masterpassPairingId;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(String socialMedia) {
		this.socialMedia = socialMedia;
	}

	public Long getSocialUserId() {
		return socialUserId;
	}

	public void setSocialUserId(Long socialUserId) {
		this.socialUserId = socialUserId;
	}

	public String getSocialUserName() {
		return socialUserName;
	}

	public void setSocialUserName(String socialUserName) {
		this.socialUserName = socialUserName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
