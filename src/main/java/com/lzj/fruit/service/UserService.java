package com.lzj.fruit.service;

import com.lzj.fruit.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static List<User> validUsers = new ArrayList<>();
    static {
        validUsers.add(new User(1L, "admin", "admin", "", "admin@test.com", ""));
        validUsers.add(new User(2L, "user", "password", "", "user@test.com", ""));
        validUsers.add(new User(3L, "张三", "password", "", "user@test.com", ""));
    }

    public static List<User> getValidUsers() {
        return validUsers;
    }

    /**
     * 处理用户登录。
     * 如果用户名和密码能够在validUsers中找到，登录成功，返回true。
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {
        /*for (User validUser : validUsers) {
            if (validUser.getUsername().equals(username) && validUser.getPassword().equals(password)) {
                return true;
            }
        }
        return false;*/
        return validUsers.stream().anyMatch(validUser ->
                validUser.getUsername().equals(username) && validUser.getPassword().equals(password));
    }
}

