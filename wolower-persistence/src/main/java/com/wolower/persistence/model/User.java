package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tUsers")
public class User {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "socialMedia")
	private String socialMedia;

	@NotNull
	@Column(name = "socialUserName")
	private String socialUserName;

	@NotNull
	@Column(name = "displayName")
	private String displayName;

	@NotNull
	@Column(name = "socialUserId")
	private String socialUserId;

	@NotNull
	@Column(name = "profileUrl")
	private String profileUrl;

	@NotNull
	@Column(name = "imageUrl")
	private String imageUrl;

	@NotNull
	@Column(name = "oauthToken")
	private String oauthToken;

	@NotNull
	@Column(name = "oauthVerifier")
	private String oauthVerifier;

	@NotNull
	@Column(name = "accessToken")
	private String accessToken;

	@NotNull
	@Column(name = "accessSecret")
	private String accessSecret;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	@NotNull
	@Column(name = "enabled")
	private Boolean enabled;

	public User() {
		this.enabled = true;
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

	public String getSocialUserName() {
		return socialUserName;
	}

	public void setSocialUserName(String socialUserName) {
		this.socialUserName = socialUserName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSocialUserId() {
		return socialUserId;
	}

	public void setSocialUserId(String socialUserId) {
		this.socialUserId = socialUserId;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getOauthVerifier() {
		return oauthVerifier;
	}

	public void setOauthVerifier(String oauthVerifier) {
		this.oauthVerifier = oauthVerifier;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessSecret() {
		return accessSecret;
	}

	public void setAccessSecret(String accessSecret) {
		this.accessSecret = accessSecret;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
