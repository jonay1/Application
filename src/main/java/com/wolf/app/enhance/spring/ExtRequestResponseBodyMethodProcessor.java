package com.wolf.app.enhance.spring;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.wolf.app.core.base.Result;
import com.wolf.app.core.bean.NormalResult;

public class ExtRequestResponseBodyMethodProcessor implements HandlerMethodReturnValueHandler {
	private final HandlerMethodReturnValueHandler delegate;

	public ExtRequestResponseBodyMethodProcessor(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		Object result = returnValue;
		if (result != null) {
			if (!(result instanceof Result)) {
				result = NormalResult.success(returnValue);
			}
		} else {
			result = NormalResult.success(null);
		}
		delegate.handleReturnValue(result, returnType, mavContainer, webRequest);
	}
}