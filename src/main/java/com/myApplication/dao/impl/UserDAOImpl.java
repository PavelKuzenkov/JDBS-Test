package com.myApplication.dao.impl;

import com.myApplication.dao.UserDAO;
import com.myApplication.data.User;
import com.myApplication.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_USER = "select * from users where id = ?";
    private final String SQL_DELETE_USER = "delete from users where id = ?";
    private final String SQL_UPDATE_USER = "update users set first_name = ?, last_name = ?, middle_name = ?, gender = ?, birthday = ? where id = ?";
    private final String SQL_GET_ALL_USERS = "select * from users";
    private final String SQL_INSERT_USER = "insert into users (id, first_name, last_name, middle_name, gender, birthday) values(?, ?, ?, ?, ?, ?)";

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER, new Object[] { id }, new UserMapper());
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public boolean deleteUser(User user) {
        return jdbcTemplate.update(SQL_DELETE_USER, user.getId()) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return jdbcTemplate.update(
                SQL_UPDATE_USER, user.getFirstName(), user.getLastName(),
                user.getMiddleName(), user.getGender().toString(),
                user.getBirthday(), user.getId()) > 0;
    }

    @Override
    public boolean createUser(User user) {
        return jdbcTemplate.update(
                SQL_INSERT_USER, user.getId(), user.getFirstName(), user.getLastName(),
                user.getMiddleName(), user.getGender().toString(), user.getBirthday()) > 0;
    }

//    @Override
//    public void createUsersTable() {
//        jdbcTemplate.execute(
//        "create table users (\n"
//            + "id serial not null primary key,\n"
//            + "first_name varchar(20) not null,\n"
//            + "last_name varchar(20) not null,\n"
//            + "middle_name varchar(20) not null,\n"
//            + "gender varchar(10) not null\n"
//            + "birthday date not null\n"
//            + ");");
//    }
//
//    @Override
//    public void deleteUsersTable() {
//        jdbcTemplate.execute("DROP TABLE IF EXISTS users;");
//    }


}
