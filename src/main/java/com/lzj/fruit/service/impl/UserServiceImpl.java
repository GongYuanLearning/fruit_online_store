package com.lzj.fruit.service.impl;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.service.UserService;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.rfc2307.RFC2307SMD5PasswordEncryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncryptor passwordEncryptor;

    public UserServiceImpl() {
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
    public User login(String username, String password) throws Exception {
        User user = userDao.getByUsernameOrEmail(username);
        return Objects.nonNull(user) && passwordEncryptor.checkPassword(password, user.getPwdHash()) ? user : null;
    }

    @Override
    public User getByUsername(String username) throws Exception {
        return this.userDao.getByUsernameOrEmail(username);
    }

    public User register(User user) throws Exception {
        // 密码进行hash处理
        user.setPwdHash(passwordEncryptor.encryptPassword(user.getPassword()));
        return userDao.create(user);
    }
}
