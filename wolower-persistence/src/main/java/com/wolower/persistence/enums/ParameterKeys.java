package com.wolower.persistence.enums;

public enum ParameterKeys {
	TWITTER_LAST_PROCESSED_POST_ID("TWITTER_LAST_PROCESSED_POST_ID");

	private final String text;

	ParameterKeys(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
