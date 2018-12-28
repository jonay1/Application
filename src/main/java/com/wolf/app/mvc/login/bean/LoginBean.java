package com.wolf.app.mvc.login.bean;

import lombok.Data;

@Data
public class LoginBean {
	private String username;
	private String password;
	private String verifycode;
}
