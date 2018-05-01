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

	@Column(name = "imageUrl")
	private String imageUrl;

	@Column(name = "thumbnailUrl")
	private String thumbnailUrl;
	
	@Column(name = "backgroundImageUrl")
	private String backgroundImageUrl;

	@Column(name = "profileBannerUrl")
	private String profileBannerUrl;

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
}
