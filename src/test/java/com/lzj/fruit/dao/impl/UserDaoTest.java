package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.entity.User;

public class UserDaoTest {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/fruits";
        String username = "root";
        String password = "password";
        UserDao userDao = new UserDaoImpl(url, username, password);

        User user = new User();
        user.setUsername("test1");
        user.setPwdHash("dkfjkdjfkdj");
        user.setEmail("test1@test.com");
        user.setPhone("12345678911");
        // 创建user
        User createdUser = userDao.create(user);

        // 通过username获取user
        User retrievedUser = userDao.getByUsername(createdUser.getUsername());

        // 更新user
        createdUser.setPhone("12345678912");
        userDao.update(createdUser);

        // 删除user
        userDao.delete(retrievedUser.getId());
    }
}
