package com.myApplication.dao;

import com.myApplication.data.User;

import java.util.List;

public interface UserDAO {

    User getUserById(Long id);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean updateUser(User user);

    void createUser(User user);

    List<User> findByParam(String param);

}
