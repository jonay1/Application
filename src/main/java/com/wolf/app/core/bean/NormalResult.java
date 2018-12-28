package com.wolf.app.core.bean;

import com.wolf.app.core.base.Code;
import com.wolf.app.core.base.Result;
import com.wolf.app.util.MessageUtil;

import lombok.Data;

@Data
public class NormalResult<T> implements Result<T> {
	private Code code = Code.SUCCESS;
	private String message;
	private T data;

	public static <T> NormalResult<T> success(T data) {
		NormalResult<T> r = new NormalResult<T>();
		r.setCode(Code.SUCCESS);
		r.setMessage(MessageUtil.getMessage(Code.SUCCESS.name()));
		r.setData(data);
		return r;
	}

	public static <T> NormalResult<T> error(Code code, Object... args) {
		NormalResult<T> r = new NormalResult<T>();
		r.setCode(code);
		r.setMessage(MessageUtil.getMessage(code.name()));
		return r;
	}
}
