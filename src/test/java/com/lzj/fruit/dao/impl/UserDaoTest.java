package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dto.SearchResult;
import com.lzj.fruit.dto.UserCriteria;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.util.DBUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 测试UserDaoImpl的功能是否符合要求。
 */
public class UserDaoTest {
    private UserDao userDao;

    /**
     * 每次测试前，做初始化工作。
     */
    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    /**
     * 每次测试后，做清理工作, 资源需要关闭。
     */
    @AfterEach
    void tearDown() throws Exception {
        try (Connection connection = DBUtil.getConnection();) {
            // 删除除admin，user之外的其他用户
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE username NOT IN ('admin', 'user', 'test1', 'test2', 'test3', 'test4', 'test5')");
            pstmt.executeUpdate();
        }
    }

    @Test
    void create() throws Exception {
        User user = new User();
        user.setUsername("lzj");
        user.setPwdHash("123456");
        user.setEmail("test@test.com");
        user.setPhone("13233333331");
        user.setBirthDate(LocalDate.now());
        User created = userDao.create(user);

        User dbUser = userDao.getByUsernameOrEmail(user.getUsername());

        assertNotNull(dbUser);
        assertNotNull(dbUser.getId());
        assertEquals(user.getUsername(), dbUser.getUsername());
        assertEquals(user.getEmail(), dbUser.getEmail());
        assertEquals(user.getPhone(), dbUser.getPhone());
        // assertEquals(user.getBirthDate(), dbUser.getBirthDate());
    }

    @Test
    void getByUsernameOrEmail() throws Exception {
        User user = userDao.getByUsernameOrEmail("user"); // 根据username获取用户

        assertEquals("user", user.getUsername());
        assertEquals("user@test.com", user.getEmail());
        assertEquals("13333333335", user.getPhone());
        assertEquals("1999-01-01", user.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        user = userDao.getByUsernameOrEmail("user@test.com"); // 根据email获取用户

        assertEquals("user", user.getUsername());
        assertEquals("user@test.com", user.getEmail());
        assertEquals("13333333335", user.getPhone());
        assertEquals("1999-01-01", user.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        user = userDao.getByUsernameOrEmail("invalid_user"); // 不存在的用户
        assertNull(user);

        user = userDao.getByUsernameOrEmail(""); // 不存在的用户
        assertNull(user);

        user = userDao.getByUsernameOrEmail(null); // 不存在的用户
        assertNull(user);
    }

    @Test
    void searchUsers() throws Exception {
        UserCriteria criteria = new UserCriteria();
        criteria.setUsername("admin"); // 通过username查询用户
        SearchResult<User> res = userDao.searchUsers(criteria);
        List<User> users = res.getData();
        assertEquals(1, res.getTotal());
        assertEquals(1, res.getTotalPages());
        assertEquals(2, res.getPageSize());
        assertEquals(0, res.getPageNumber());
        assertEquals(1, users.size());
        assertEquals(criteria.getUsername(), users.get(0).getUsername());

        criteria = new UserCriteria();
        criteria.setEmail("admin@test.com"); // 通过email查询用户
        res = userDao.searchUsers(criteria);
        users = res.getData();
        assertEquals(1, res.getTotal());
        assertEquals(1, res.getTotalPages());
        assertEquals(2, res.getPageSize());
        assertEquals(0, res.getPageNumber());
        assertEquals(1, users.size());
        assertEquals(criteria.getEmail(), users.get(0).getEmail());

        criteria = new UserCriteria();
        criteria.setSortBy("email");
        criteria.setSortDirection("desc");
        criteria.setPageNumber(1);
        res = userDao.searchUsers(criteria);
        users = res.getData();
        assertEquals(7, res.getTotal());
        assertEquals(4, res.getTotalPages());
        assertEquals(2, res.getPageSize());
        assertEquals(1, res.getPageNumber());
        assertEquals(2, users.size());
        assertEquals("test4@test.com", users.get(0).getEmail());
        assertEquals("test3@test.com", users.get(1).getEmail());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}