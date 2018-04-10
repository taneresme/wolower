package com.wolower.ui.social;

import java.time.LocalDateTime;

public class SocialPost {
	private String socialMedia;
	private Long socialUserId;
	private String socialUserName;
	private Long amount;
	private String currency;
	private LocalDateTime postDate;
	private Long postId;
	private String postText;
	private Boolean isReply;
	private Long repliedPostId;

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

	public LocalDateTime getPostDate() {
		return postDate;
	}

	public void setPostDate(LocalDateTime postDate) {
		this.postDate = postDate;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public Boolean getIsReply() {
		return isReply;
	}

	public Long getRepliedPostId() {
		return repliedPostId;
	}

	public void setRepliedPostId(Long repliedPostId) {
		this.repliedPostId = repliedPostId;
		this.isReply = !(repliedPostId == null);
	}

}
