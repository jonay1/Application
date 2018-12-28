package com.wolf.app.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.wolf.app.core.security.ExtSecurityInterceptor;
import com.wolf.app.enhance.spring.ExtExceptionHandlerExceptionResolver;
import com.wolf.app.enhance.spring.ExtRequestResponseBodyMethodProcessor;
import com.wolf.app.enhance.spring.interceptor.ExtLogInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private ExtLogInterceptor logInterceptor;
	@Autowired
	private ExtSecurityInterceptor secInterceptor;
	@Autowired
	private ExtExceptionHandlerExceptionResolver exceptionResovler;

	@Override
	public ExtExceptionHandlerExceptionResolver createExceptionHandlerExceptionResolver() {
		return exceptionResovler;
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
		registry.addInterceptor(secInterceptor);
	}

//	@Bean
//	public HandlerInterceptor newSecurityInterceptor() {
//		return new ExtSecurityInterceptor();
//	}

	@Autowired
	public void decorateRequestMappingHandlerAdapter(RequestMappingHandlerAdapter adapter) {
		List<HandlerMethodReturnValueHandler> handlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> newhandlers = new ArrayList<>(handlers);
		decorateHandlers(newhandlers);
		adapter.setReturnValueHandlers(newhandlers);
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				// 用自己的ResponseBody包装类替换掉框架的，达到返回Result的效果
				ExtRequestResponseBodyMethodProcessor decorator = new ExtRequestResponseBodyMethodProcessor(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				break;
			}
		}
	}

}