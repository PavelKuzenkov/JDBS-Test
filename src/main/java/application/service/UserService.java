package application.service;

import application.data.User;

import java.util.List;

public interface UserService {

    void toJSON(User user);

    User toJavaObject(String fileName);

    List<User> getAllUsers();
}