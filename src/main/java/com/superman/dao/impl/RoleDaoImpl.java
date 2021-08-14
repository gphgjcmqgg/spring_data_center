package com.superman.dao.impl;

import com.superman.dao.RoleDao;
import com.superman.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Role> findAll() {
        List<Role> roleList = jdbcTemplate.query("select * from sys_role", new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }

    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role (roleName, roleDesc) values (?,?)", role.getRoleName(), role.getRoleDesc());
    }

    public List<Role> findRoleByUserId(Long uId) {
        List<Role> roleList = jdbcTemplate.query("select * from sys_role where id in (select roleId from sys_user_role where userId = ?)", new BeanPropertyRowMapper<Role>(Role.class), uId);
        return roleList;
    }


}
