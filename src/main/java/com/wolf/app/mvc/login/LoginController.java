package com.wolf.app.mvc.login;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wolf.app.core.bean.SessionUtil;
import com.wolf.app.mvc.login.bean.LoginBean;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {
	private static final String PWD_KEY = "$ENC_KEY";

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(@RequestBody LoginBean bean) {
		String s = "你好";
		return s;
	}

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String getToken() {
		String key = RandomStringUtils.randomAlphabetic(16);
		SessionUtil.setSessionAttribute(PWD_KEY, key);
		return key;
	}

}
