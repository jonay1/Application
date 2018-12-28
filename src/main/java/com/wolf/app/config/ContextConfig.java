package com.wolf.app.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.wolf.app.util.ContextUtil;
import com.wolf.app.util.MessageUtil;
import com.wolf.app.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Order(-1)
@Slf4j
public class ContextConfig {

	@Autowired
	private Environment env;
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private MessageSource ms;

	@PostConstruct
	public void init() {
		log.info("绑定资源文件上下文");
		MessageUtil.set(ms);
		log.info("绑定配置文件上下文");
		PropertiesUtil.set(env);
		log.info("绑定容器上下文");
		ContextUtil.set(ctx);
	}

}
