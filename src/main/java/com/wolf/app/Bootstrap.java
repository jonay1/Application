package com.wolf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 应用入口
 * 
 * @author jonay
 *
 */
@SpringBootApplication
public class Bootstrap extends SpringBootServletInitializer {

	/**
	 * jar启动
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(Bootstrap.class, args);
	}

	/**
	 * tomcat war启动
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Bootstrap.class);
	}
}
