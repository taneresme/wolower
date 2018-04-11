package com.wolower.ui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {
	private static final Pattern pattern = Pattern.compile("\\$((?:[0-9])*(?:[\\\\.]|[\\\\,])?(?:[0-9]{1,2}))");

	public static Long extractPrice(String post) {
		Matcher matcher = pattern.matcher(post);
		Boolean matches = matcher.find();
		if (matches) {
			String amountStr = String.format("%.2f", Float.valueOf(matcher.group(1).replace(',', '.')));
			amountStr = amountStr.replace(".", "").replace(",", "");
			return Long.valueOf(amountStr);
		}
		return null;
	}
}
