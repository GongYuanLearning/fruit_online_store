package com.lzj.fruit.dao;

import com.lzj.fruit.entity.User;

public interface UserDao {
    /**
     * 创建用户
     * @return
     */
    User create(User user) throws Exception;
    /**
     * 通过用户名查询用户
     * @return
     */
    User getByUsername(String username) throws Exception;

    /**
     * 更新用户
     * @param user
     * @return
     */
    User update(User user) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    void delete(long id) throws Exception;
}

