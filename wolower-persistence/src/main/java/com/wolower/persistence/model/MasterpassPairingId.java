package com.wolower.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.wolower.persistence.enums.PairingIdSources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tMasterpassPairingIds")
public class MasterpassPairingId {
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
	@Column(name = "source")
	private PairingIdSources source;

	@NotNull
	@Column(name = "pairingId")
	private String pairingId;

	@NotNull
	@Column(name = "wasted")
	private Boolean wasted;

	@NotNull
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	public MasterpassPairingId() {
		this.timestamp = LocalDateTime.now();
		this.wasted = false;
	}
}
