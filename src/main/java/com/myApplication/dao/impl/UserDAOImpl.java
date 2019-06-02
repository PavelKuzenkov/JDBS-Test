package com.myApplication.dao.impl;

import com.myApplication.Gender;
import com.myApplication.dao.UserDAO;
import com.myApplication.data.User;
import com.myApplication.service.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired private DataSourceTransactionManager transactionManager;

    private final String SQL_FIND_USER = "select * from users where id = ?";
    private final String SQL_DELETE_USER = "delete from users where id = ?";
    private final String SQL_UPDATE_USER = "update users set first_name = ?, last_name = ?, middle_name = ?, gender = ?, birthday = ? where id = ?";
    private final String SQL_GET_ALL_USERS = "select * from users";
    private final String SQL_GET_USER_WITH_PARAM = "select * from users where first_name like ?1 or last_name like ?1 or middle_name like ?1 or gender like ?1 or birthday = ?1";
//    private final String SQL_GET_USER_WITH_LASTNAME = "select * from users where first_name like ? or last_name like ? or middle_name like ? or gender like ? or birthday = ?";
//    private final String SQL_GET_USER_WITH_MIDDLENAME = "select * from users where first_name like ? or last_name like ? or middle_name like ? or gender like ? or birthday = ?";
//    private final String SQL_GET_USER_WITH_FIRSTNAME = "select * from users where first_name like ? or last_name like ? or middle_name like ? or gender like ? or birthday = ?";
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
    @Transactional
    public void createUser(User user) {
        jdbcTemplate.update(
                SQL_INSERT_USER, user.getId(), user.getFirstName(), user.getLastName(),
                user.getMiddleName(), user.getGender().toString(), user.getBirthday());
    }

    @Override
    public List<User> findByParam(String param) {
        List<User> result = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(SQL_GET_USER_WITH_PARAM, param);
        for (Map<String, Object> map : maps) {
            User user = new User();
            user.setId((Long)map.get("id"));
            user.setFirstName((String)map.get("first_name"));
            user.setLastName((String)map.get("last_name"));
            user.setMiddleName((String)map.get("middle_name"));
            user.setGender(map.get("gender").equals("MALE") ? Gender.MALE : Gender.FEMALE);
            Date birthday = (Date) map.get("birthday");
            user.setBirthday(birthday.toLocalDate());
            result.add(user);
        }
        return result;
    }
}
