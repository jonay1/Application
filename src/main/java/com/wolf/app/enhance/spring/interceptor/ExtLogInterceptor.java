package com.wolf.app.enhance.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wolf.app.util.LogUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "LOG")
@Component
public class ExtLogInterceptor extends HandlerInterceptorAdapter {
	private ThreadLocal<StopWatch> watch = new ThreadLocal<StopWatch>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		StopWatch stopWatch = new StopWatch();
		watch.set(stopWatch);
		stopWatch.start();
		LogUtil.setupMDC();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		log.info("##############{} {} BEGIN##############", request.getMethod(), request.getRequestURI());
		log.info("invoke {}", handlerMethod.getShortLogMessage());
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		StopWatch stopWatch = watch.get();
		stopWatch.stop();
		log.info("##############{} {} END, COST {}s##############", request.getMethod(), request.getRequestURI(),
				stopWatch.getTotalTimeSeconds());
		LogUtil.clearMDC();
	}
}
