package com.wolf.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {
	private boolean enabled = true;
	private String excludes;
	private String includes;
	private String sensitiveChars;

	private String refererAccepts;
	private String refererIgnores;
}
