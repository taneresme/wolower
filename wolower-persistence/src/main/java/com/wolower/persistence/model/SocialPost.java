package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wolower.persistence.enums.PostTypes;

@Entity
@Table(name = "tSocialPosts")
public class SocialPost {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "socialMedia")
	private String socialMedia;

	@NotNull
	@Column(name = "socialUserId")
	private String socialUserId;

	@NotNull
	@Column(name = "amount")
	private Long amount;

	@NotNull
	@Column(name = "currency")
	private String currency;

	@NotNull
	@Column(name = "postType")
	@Enumerated(EnumType.STRING)
	private PostTypes postType;

	@Column(name = "postId")
	private String postId;

	@Column(name = "postDate")
	private LocalDateTime postDate;

	@Column(name = "postText")
	private String postText;

	@NotNull
	@Column(name = "postUrl")
	private String postUrl;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public SocialPost() {
		this.timestamp = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(String socialMedia) {
		this.socialMedia = socialMedia;
	}

	public String getSocialUserId() {
		return socialUserId;
	}

	public void setSocialUserId(String socialUserId) {
		this.socialUserId = socialUserId;
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

	public PostTypes getPostType() {
		return postType;
	}

	public void setPostType(PostTypes postType) {
		this.postType = postType;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
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

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
