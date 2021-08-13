package com.superman.service.impl;

import com.superman.dao.RoleDao;
import com.superman.domain.Role;
import com.superman.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> list() {
        List<Role> roleList = roleDao.findAll();

        return roleList;
    }
}
