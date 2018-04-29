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
@Table(name = "tOrder")
public class Order {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "userId")
	private int userId;

	@NotNull
	@Column(name = "productId")
	private int productId;

	@NotNull
	@Column(name = "socialMedia")
	private String socialMedia;

	@NotNull
	@Column(name = "socialUserId")
	private Long socialUserId;

	@NotNull
	@Column(name = "socialUserName")
	private String socialUserName;

	@NotNull
	@Column(name = "price")
	private Long price;

	@NotNull
	@Column(name = "count")
	private int count;

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

	@NotNull
	@Column(name = "paid")
	private Boolean paid;

	public Order() {
		this.timestamp = LocalDateTime.now();
		this.count = 1;
		this.paid = false;
	}
}
