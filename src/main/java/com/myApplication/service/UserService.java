package com.myApplication.service;

import com.myApplication.data.User;

import java.util.List;

public interface UserService {

  void toJSON(User user);

  User toJavaObject(String fileName);

  List<User> getAllUsers();
}
