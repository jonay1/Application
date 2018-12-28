package com.wolf.app.core.security;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

import org.springframework.util.Assert;

/**
 * Delegating implementation of {@link javax.servlet.ServletInputStream}.
 */
public class DelegatingServletInputStream extends ServletInputStream {

	private final InputStream sourceStream;

	/**
	 * Create a DelegatingServletInputStream for the given source stream.
	 * 
	 * @param sourceStream
	 *            the source stream (never {@code null})
	 */
	public DelegatingServletInputStream(InputStream sourceStream) {
		Assert.notNull(sourceStream, "Source InputStream must not be null");
		this.sourceStream = sourceStream;
	}

	/**
	 * Return the underlying source stream (never {@code null}).
	 */
	public final InputStream getSourceStream() {
		return this.sourceStream;
	}

	@Override
	public int read() throws IOException {
		return this.sourceStream.read();
	}

	@Override
	public void close() throws IOException {
		super.close();
		this.sourceStream.close();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener listener) {
	}

}
