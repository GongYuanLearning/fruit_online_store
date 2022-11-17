package com.lzj.fruit.service;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.entity.User;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.rfc2307.RFC2307SMD5PasswordEncryptor;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserDao userDao;
    private final PasswordEncryptor passwordEncryptor;

    public UserService() {
        userDao = new UserDaoImpl();
        passwordEncryptor = new RFC2307SMD5PasswordEncryptor();
    }

    private static List<User> validUsers = new ArrayList<>();
    static {
        validUsers.add(new User(1L, "admin", "admin", "", "admin@test.com", "", null));
        validUsers.add(new User(2L, "user", "password", "", "user@test.com", "", null));
        validUsers.add(new User(3L, "张三", "password", "", "user@test.com", "", null));
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

    public User register(User user) throws Exception {
        user.setPwdHash(passwordEncryptor.encryptPassword(user.getPassword()));
        return userDao.create(user);
    }
}

