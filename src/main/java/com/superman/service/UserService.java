package com.superman.service;

import com.superman.domain.User;

import java.util.List;

public interface UserService {

    List<User> list();

    void save(User user, Long[] roleIds);

    void delUser(Long id);

    User login(String username, String password);
}
