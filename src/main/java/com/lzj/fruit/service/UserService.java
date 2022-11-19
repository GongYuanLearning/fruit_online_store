package com.lzj.fruit.service;

import com.lzj.fruit.entity.User;

public interface UserService {
    User register(User user) throws Exception;
    User login(String username, String password) throws Exception;
    User getByUsername(String username) throws Exception;
}
