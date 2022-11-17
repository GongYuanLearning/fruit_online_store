package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

@AllArgsConstructor
@Data
public class UserDaoImpl implements UserDao {
    static {
        try {
            // 1. 加载database驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // ignore
        }
    }

    private String url;
    private String username;
    private String password;

    @Override
    public User create(User user) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("INSERT INTO user(username, pwdHash, email,  phone) VALUES (?, ?, ?, ?)")
        ) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPwdHash());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.executeUpdate();

//            Date now = new Date();
//            java.sql.Date date = new java.sql.Date(now.getTime());
//            stmt.executeUpdate(String.format("INSERT INTO user(username, pwdHash, email,  phone) VALUES ('%s', '%s', '%s', '%s')",
//                    user.getUsername(), user.getPwdHash(), user.getEmail(), user.getPhone()));
        }
        return user;
    }

    @Override
    public User getByUsername(String username) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库

        try (Connection connection = DriverManager.getConnection(url, this.username, password);
             PreparedStatement pstmt = connection.prepareStatement("SELECT id, username, email, phone FROM user where username=?")
        ) {
            // SELECT id, username, email, phone FROM user where username=lzj
//            ResultSet rs = stmt.executeQuery(String.format("SELECT id, username, email, phone FROM user where username='%s'", username));

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if (rs.next()) { // 将指针移动到第一条记录（如果有记录）
                user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
            }
            return user;
        }
    }

    @Override
    public User update(User user) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("UPDATE user SET phone=? WHERE username=?")
        ) {
//            stmt.executeUpdate(String.format("UPDATE user SET phone='%s' WHERE username='%s'", user.getPhone(), user.getUsername()));
            pstmt.setString(1, user.getPhone());
            pstmt.setString(2, user.getUsername());
        }
        return user;
    }

    @Override
    public void delete(long id) throws Exception {
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement("DELETE FROM user WHERE id=?")
        ) {
//            stmt.executeUpdate(String.format("DELETE FROM user WHERE id=%d", id));
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
