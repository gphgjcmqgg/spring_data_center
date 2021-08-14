package com.superman.dao;

import com.superman.domain.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    Long saveUser(User user);

    void userSaveRelation(Long userId, Long[] roleIds);

    void delUser(Long id);

    void delRealation(Long id);
}
