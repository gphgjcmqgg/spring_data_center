package com.superman.service.impl;

import com.superman.dao.RoleDao;
import com.superman.dao.UserDao;
import com.superman.domain.Role;
import com.superman.domain.User;
import com.superman.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<User> list() {
        List<User> userList = userDao.findAll();

        if(userList!= null && userList.size() > 0) {
            for (User user : userList) {
                Long uId = user.getId();

                List<Role> roleList = roleDao.findRoleByUserId(uId);

                user.setRoles(roleList);
            }
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        Long userId = userDao.saveUser(user);
        userDao.userSaveRelation(userId, roleIds);

    }

    public void delUser(Long id) {
        userDao.delRealation(id);
        userDao.delUser(id);
    }

    public User login(String username, String password) {
        User user = null;
        try {
            user =  userDao.login(username, password);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
}
