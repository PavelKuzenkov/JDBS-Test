package com.myApplication.dao;

import com.myApplication.data.User;

import java.util.List;

public interface UserDAO {

    User getUserById(Long id);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean updateUser(User user);

    boolean createUser(User user);

//    void createUsersTable();
//
//    void deleteUsersTable();
}
