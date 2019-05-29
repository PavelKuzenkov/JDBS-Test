package com.myApplication.service;

import com.myApplication.Gender;
import com.myApplication.data.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setGender(rs.getString("gender").equals("MALE") ? Gender.MALE : Gender.FEMALE);
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
