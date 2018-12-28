package com.wolf.app.core.exception;

import com.wolf.app.core.base.Code;
import com.wolf.app.util.MessageUtil;

@SuppressWarnings("serial")
public class AppException extends RuntimeException {

	private Code code;
	private Object[] args;
	private boolean error;

	private AppException(Code code, Object... args) {
		this.code = code;
		this.args = args;
		if (args != null && args.length > 0 && args[args.length - 1] instanceof Throwable) {
			this.error = true;
			this.initCause((Throwable) args[args.length - 1]);
		}
	}

	public String getCode() {
		return code.name();
	}

	public boolean hasError() {
		return this.error;
	}

	@Override
	public String getMessage() {
		return MessageUtil.getMessage(getCode(), args);
	}

	@Override
	public String toString() {
		return getMessage();
	}

	public static AppException of(Code code, Object... args) {
		return new AppException(code, args);
	}

	public static AppException wrap(Throwable e) {
		if (e instanceof AppException) {
			return (AppException) e;
		} else {
			return new AppException(Code.ERROR, e);
		}
	}

}
