package com.wolf.app.core.bean;

import com.wolf.app.core.base.SessionUser;

import lombok.Data;

@Data
public class UserInfo implements SessionUser {

	private int id;
	private String name;

}
