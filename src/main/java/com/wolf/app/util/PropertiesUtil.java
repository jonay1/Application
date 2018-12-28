package com.wolf.app.util;

import org.springframework.core.env.Environment;

public class PropertiesUtil {

	private static Environment env;

	public static void set(Environment env) {
		PropertiesUtil.env = env;
	}

	public static String getString(String key, String defaultValue) {
		return env.getProperty(key, defaultValue);
	}

	public static int getInt(String key, int defaultValue) {
		return env.getProperty(key, Integer.TYPE, defaultValue);
	}

	public static boolean getBool(String key, boolean defaultValue) {
		return env.containsProperty(key) ? env.getProperty(key, Boolean.TYPE) : defaultValue;
	}

}
