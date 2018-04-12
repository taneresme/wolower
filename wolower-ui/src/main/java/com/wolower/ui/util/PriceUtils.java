package com.wolower.ui.util;

public class PriceUtils {
	public static final String PRICE_STRING_FORMAT = "%.2f";

	public static String toString(Long price) {
		return String.format(PRICE_STRING_FORMAT, (float) price / 100);
	}

	public static Long toLong(String price) {
		String inPrice = price.replace(',', '.');
		String amountStr = String.format(PRICE_STRING_FORMAT, Float.valueOf(inPrice));
		amountStr = amountStr.replace(".", "").replace(",", "");
		return Long.valueOf(amountStr);
	}
}
