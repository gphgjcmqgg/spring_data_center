package com.superman.dao.impl;

import com.superman.dao.UserDao;
import com.superman.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {

        List<User> userList = jdbcTemplate.query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    public Long saveUser(final User user) {
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement preparedStatement = connection.prepareStatement(
                        "insert into sys_user (id, username,email,password, phoneNum) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setObject(1, null);
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getPhoneNum());

                return preparedStatement;
            }

        };

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator, keyHolder);

        Long key = keyHolder.getKey().longValue();
        return key;
    }

    public void userSaveRelation(Long userId, Long[] roleIds) {
        for (Long roleId : roleIds) {
            jdbcTemplate.update("insert into sys_user_role (userId, roleId) values(?,?)", userId, roleId);
        }
    }

    public void delUser(Long id) {
        jdbcTemplate.update("delete from sys_user where id=?", id);
    }

    public void delRealation(Long id) {
        jdbcTemplate.update("delete from sys_user_role where userId=?", id);
    }

    public User login(String username, String password) throws EmptyResultDataAccessException {
        User user = jdbcTemplate.queryForObject("select * from sys_user where username=? and password = ?", new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
    }
}
