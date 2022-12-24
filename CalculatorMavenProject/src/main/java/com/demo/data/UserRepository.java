package com.demo.data;

import com.demo.model.User;

public interface UserRepository {

    boolean save(User user);
}
