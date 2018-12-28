package com.wolf.app.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.jboss.logging.MDC;

public class LogUtil {

	private static String MDC_THREAD_KEY = "TID";

	public static void setupMDC() {
		MDC.put(MDC_THREAD_KEY, RandomStringUtils.randomAlphabetic(10));
	}

	public static void clearMDC() {
		MDC.remove(MDC_THREAD_KEY);
	}
}
