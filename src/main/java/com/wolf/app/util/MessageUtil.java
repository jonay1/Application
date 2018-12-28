package com.wolf.app.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class MessageUtil {

	private static MessageSource ms;

	public static void set(MessageSource ms) {
		MessageUtil.ms = ms;
	}

	public static String getMessage(String code, Object... args) {
		return ms.getMessage(code, args, code, Locale.getDefault());
	}

}
