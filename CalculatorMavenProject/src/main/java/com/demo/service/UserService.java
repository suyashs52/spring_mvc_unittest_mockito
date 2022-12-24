package com.demo.service;

import com.demo.model.User;

public interface UserService {
    User createUser(String firstName, String lastName, String email, String password, String repeatPass);
}
