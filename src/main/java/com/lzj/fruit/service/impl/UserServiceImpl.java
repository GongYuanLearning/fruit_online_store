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

    /**
     * 处理用户登录。
     * 如果用户名和密码能够在validUsers中找到，登录成功，返回true。
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) throws Exception {
        User user = userDao.getByUsernameOrEmail(username);
        return Objects.nonNull(user) && passwordEncryptor.checkPassword(password, user.getPwdHash());
    }

    @Override
    public User getByUsername(String username) throws Exception {
        return this.userDao.getByUsernameOrEmail(username);
    }

    /**
     * 注册用户。通过添加用户到数据库完成注册。
     * @param user
     * @return
     * @throws Exception
     */
    public User register(User user) throws Exception {
        // 密码进行hash处理
        user.setPwdHash(passwordEncryptor.encryptPassword(user.getPassword()));
        return userDao.create(user);
    }
}
