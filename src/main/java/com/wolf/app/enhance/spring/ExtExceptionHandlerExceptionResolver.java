package com.wolf.app.enhance.spring;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wolf.app.core.exception.AppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ERROR")
@Component
public class ExtExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		AppException ex = AppException.wrap(exception);
		if (handlerMethod != null) {
			Logger logger = LoggerFactory.getLogger(handlerMethod.getBeanType());
			logger.error("发生异常", ex);
		} else {
			log.error("发生异常", ex);
		}
		ModelAndView view = null;
		Method method = null;
		if (handlerMethod != null) {
			method = handlerMethod.getMethod();
		}

		if (method != null) {
			boolean isJson = true;
			ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
			if (responseBody == null) {
				RestController restController = AnnotationUtils.findAnnotation(method.getDeclaringClass(),
						RestController.class);
				if (restController == null) {
					isJson = false;
				}
			}

			if (isJson) {
				MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
				Map<String, Object> map = new HashMap<>();
				map.put("code", ex.getCode());
				map.put("message", ex.getMessage());
				view = new ModelAndView(jsonView, map);
			}
		}

		if (view == null) {
			// response.setStatus(500);
			view = new ModelAndView();
			view.addObject("code", ex.getCode());
			view.addObject("message", ex.getMessage());
			view.setViewName("500");
		}
		return view;
	}

}
