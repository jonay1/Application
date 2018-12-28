package com.wolf.app.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;

public class ContextUtil {

	private static ApplicationContext ctx = null;

	public static void set(ApplicationContext applicationContext) {
		if (ContextUtil.ctx == null) {
			ContextUtil.ctx = applicationContext;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) ctx.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return ctx.getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return ctx.getBean(name, clazz);
	}

	public static Map<String, Object> getBeansByAnnotation(Class<? extends Annotation> annotationType) {
		return ctx.getBeansWithAnnotation(annotationType);
	}

	public static <T> Map<String, T> getBeansByType(Class<T> clazz) {
		return ctx.getBeansOfType(clazz);
	}

}
