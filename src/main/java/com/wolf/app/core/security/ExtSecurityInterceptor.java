package com.wolf.app.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wolf.app.config.SecurityConfig;
import com.wolf.app.core.base.Code;
import com.wolf.app.core.base.SessionUser;
import com.wolf.app.core.bean.SessionUtil;
import com.wolf.app.core.exception.AppException;
import com.wolf.app.tools.MapCache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExtSecurityInterceptor extends HandlerInterceptorAdapter {

	private static final String CACHE_KEY = "security.interceptor";

	protected AntPathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private SecurityConfig config;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (config.isEnabled()) {
			boolean crsf = crsfCheck(request);
			if (!crsf) {
				throw AppException.of(Code.INVALID_REFER);
			}
			boolean check = dataCheck(request);
			if (!check) {
				throw AppException.of(Code.ILLEGAL_DATA);
			}
			String requestURI = request.getRequestURI();
			if (!config.isEnabled() || isExclued(requestURI)) {
				return true;
			}
			SessionUser user = SessionUtil.getSessionUser(false);
			if (user == null) {
				log.warn("do login to access {}", requestURI);
				throw AppException.of(Code.INVALID_SESSION);
			}
		}
		return true;
	}

	private boolean isExclued(String requestURI) {
		MapCache cache = MapCache.getInstance(CACHE_KEY);
		Boolean exclude = cache.get(requestURI);
		if (exclude != null) {
			return exclude;
		}

		exclude = Boolean.FALSE;
		String excludes = config.getExcludes();
		if (StringUtils.isNotBlank(excludes)) {
			String[] split = excludes.split(",");
			for (String s : split) {
				boolean match = pathMatcher.match(s, requestURI);
				if (match) {
					exclude = Boolean.TRUE;
					break;
				}
			}
		}
		cache.put(requestURI, exclude);
		return exclude;
	}

	/**
	 * 防御xss，sql注入
	 * 
	 * @param request
	 * @return
	 */
	private boolean dataCheck(HttpServletRequest request) {
		if (request instanceof SecurityHttpRequestWrapper) {
			SecurityHttpRequestWrapper sreq = (SecurityHttpRequestWrapper) request;
			String body = sreq.getBody();
			if (StringUtils.containsAny(body, config.getSensitiveChars())) {
				log.debug("FORBIDDEN: request body contains one of sensitive chars [{}]", config.getSensitiveChars());
				return false;
			}
		}
		return true;
	}

	private boolean crsfCheck(HttpServletRequest request) {
		if (request instanceof SecurityHttpRequestWrapper) {
			SecurityHttpRequestWrapper sreq = (SecurityHttpRequestWrapper) request;
			// csrf
			String ignores = config.getRefererIgnores();
			Boolean ignore = Boolean.FALSE;
			if (StringUtils.isNotBlank(ignores)) {
				String requestURI = sreq.getRequestURI();
				String[] split = ignores.split(",");
				for (String s : split) {
					boolean match = pathMatcher.match(s, requestURI);
					if (match) {
						ignore = Boolean.TRUE;
						break;
					}
				}
			}
			if (!ignore) {
				String ref = sreq.getHeader("Referer");
				Boolean accept = Boolean.FALSE;
				String accepts = config.getRefererAccepts();
				if (StringUtils.isNotBlank(accepts)) {
					if (StringUtils.isNotBlank(ref)) {
						ref = ref.replaceAll("http://|https://", "");
						String[] split = accepts.split(",");
						for (String s : split) {
							if (ref.startsWith(s)) {
								accept = Boolean.TRUE;
								break;
							}
						}
					}
				} else {
					accept = Boolean.TRUE;
				}
				if (!accept) {
					log.debug("FORBIDDEN: request referer is not accept, accepts '{}', actual '{}'",
							config.getRefererAccepts(), ref);
					return false;
				}
			}
		}
		return true;
	}

}
