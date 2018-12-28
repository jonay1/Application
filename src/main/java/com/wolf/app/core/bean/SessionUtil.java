package com.wolf.app.core.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wolf.app.core.base.Code;
import com.wolf.app.core.base.SessionUser;
import com.wolf.app.core.exception.AppException;

public class SessionUtil {

	private static final String NAME = "$session_user";

	public static <T extends SessionUser> T getSessionUser() {
		return getSessionUser(false);
	}

	@SuppressWarnings("unchecked")
	public static <T extends SessionUser> T getSessionUser(boolean need) {
		SessionUser user = null;
		HttpSession session = getSession(false);
		if (session != null) {
			user = (SessionUser) session.getAttribute(NAME);
		}
		if (user == null && need) {
			throw AppException.of(Code.INVALID_SESSION);
		}
		return (T) user;
	}

	public static void setSessionUser(SessionUser user) {
		HttpSession session = getSession(true);
		session.setAttribute(NAME, user);
	}

	public static HttpSession getSession() {
		return getSession(true);
	}

	public static void invalidateSession() {
		getSession().invalidate();
	}

	public static HttpSession getSession(boolean need) {
		HttpSession session = null;
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			session = request.getSession(need);
		}
		return session;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(String key) {
		return (T) getSession().getAttribute(key);
	}

	public static void setSessionAttribute(String key, Object value) {
		getSession().setAttribute(key, value);
	}
}
