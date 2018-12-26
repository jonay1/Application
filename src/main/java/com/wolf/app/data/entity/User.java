package com.wolf.app.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	@Id
	private int id;

	private String username;
	private String password;
	
}
