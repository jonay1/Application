package com.wolf.app.core.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(filterName = "sescurityHttpRequestWrapperFilter", urlPatterns = "/*")
public class SecurityHttpRequestWrapperFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest srequest = request;
		if (request instanceof HttpServletRequest) {
			srequest = new SecurityHttpRequestWrapper((HttpServletRequest) request);
		}
		chain.doFilter(srequest, response);
	}

	@Override
	public void destroy() {

	}

}