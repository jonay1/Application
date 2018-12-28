package com.wolf.app.core.security;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;


public class SecurityHttpRequestWrapper extends HttpServletRequestWrapper {
	private static final String UTF_8 = "utf-8";
	private String body;
	private boolean isFile = false;

	public SecurityHttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		isFile = isMultipartContent(request);
		if (!isFile) {
			body = IOUtils.toString(request.getInputStream(), UTF_8);
		}

	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (isFile) {
			return super.getInputStream();
		}

		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(UTF_8));
		return new DelegatingServletInputStream(byteArrayInputStream);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (isFile) {
			return super.getReader();
		}
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public String getBody() {
		if (isFile) {
			return null;
		}
		return body;
	}

	private static final String MULTIPART = "multipart/";

	private boolean isMultipartContent(HttpServletRequest request) {
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String contentType = request.getContentType();
		if (contentType == null) {
			return false;
		}
		if (contentType.toLowerCase().startsWith(MULTIPART)) {
			return true;
		}
		return false;
	}

}
