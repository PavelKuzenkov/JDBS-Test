package com.myApplication.dao;

import com.myApplication.data.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {

    User getUserById(Long id);

    List<User> getAllUsers();

    boolean deleteUser(User user);

    boolean updateUser(User user);

    void createUser(User user);

    Set<User> findByParam(String param);

    List<User> findByFirstName(String param);

    List<User> findByLastName(String param);

    List<User> findByMiddleName(String param);
}
