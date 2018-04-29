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
@Table(name = "tBalances")
public class Balance {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "userId")
	private int userId;

	@NotNull
	@Column(name = "balance")
	private Long balance;
	
	@NotNull
	@Column(name = "currency")
	private String currency;

	@NotNull
	@Column(name = "lastUpdateTimestamp")
	private LocalDateTime lastUpdateTimestamp;

	public Balance() {
		this.lastUpdateTimestamp = LocalDateTime.now();
	}

}
