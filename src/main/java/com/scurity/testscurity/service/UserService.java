package com.scurity.testscurity.service;

import com.scurity.testscurity.entities.User;

import java.util.List;

public interface UserService {
    User AddUser(User user);

    List<User> getUsers();

}