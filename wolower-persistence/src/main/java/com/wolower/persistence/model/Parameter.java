package com.wolower.persistence.model;

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
@Table(name = "tParameters")
public class Parameter {
	@Id
	@GeneratedValue
	@NotNull
	@Column(name = "id")
	private int id;

	@NotNull
	@Column(name = "parameterKey", unique = true)
	private String parameterKey;

	@NotNull
	@Column(name = "parameterValue")
	private String parameterValue;

	public Parameter() {
	}

	public Parameter(@NotNull String parameterKey, @NotNull String parameterValue) {
		this.parameterKey = parameterKey;
		this.parameterValue = parameterValue;
	}

}
