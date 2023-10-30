package com.lzj.fruit.util;

import com.lzj.fruit.dao.impl.UserDaoImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public final class DBUtil {
    private static String url;
    private static String username;
    private static String password;

    static {
        // 使用ClassLoader加载类路径下的application.properties作为输入流
        try (InputStream in = UserDaoImpl.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(in); // 加载properties文件格式的输入流
            String driverClass = properties.getProperty("datasource.driver_class");
            url = properties.getProperty("datasource.url");
            username = properties.getProperty("datasource.username");
            password = properties.getProperty("datasource.password");

            // 1. 加载database驱动类
            Class.forName(driverClass);
        } catch (Exception e) {
            // ignore
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, username, password);
    }
}

