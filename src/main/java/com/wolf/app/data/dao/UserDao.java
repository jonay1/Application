package com.wolf.app.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wolf.app.data.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {

}
