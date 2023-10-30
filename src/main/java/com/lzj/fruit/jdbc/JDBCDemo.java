package com.lzj.fruit.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo {

    public static void main(String[] args) throws Exception {
        // 1. 加载database驱动类
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 2. 使用DriverManager获取数据库连接
        // jdbc:mysql是协议，localhost是主机名，3306是db服务器的端口， fruits代表的是要连接的数据库
        String url = "jdbc:mysql://localhost:3306/fruits";
        String username = "root";
        String password = "my-secret-pw";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement stmt = connection.createStatement()
        ) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            System.out.println(
                    String.format("数据库版本：%d.%d",
                            dbMetaData.getDatabaseMajorVersion(),
                            dbMetaData.getDatabaseMinorVersion()));
            System.out.println(String.format("数据库URL：%s", dbMetaData.getURL()));
            System.out.println(String.format("数据库连接用户名：%s", dbMetaData.getUserName()));

//            boolean res = stmt.execute("SELECT id, username, email, phone FROM user");
//            boolean res = stmt.execute("delete from user");
//            System.out.println("查询user表后，有没有结果: " + res);

            // 3. 操作数据库，这里是查询
            ResultSet rs = stmt.executeQuery("SELECT id, username, email, phone FROM user");
            int i = 0;

            // 4. 处理查询结果
            while (rs.next()) { // next用于将结果集的游标往下移动一个位置，返回结果代表移动后的位置有没有记录
                i++;
//                System.out.println(String.format("第%d行：%d, %s, %s, %s", i,
//                        rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                System.out.println(String.format("第%d行：%d, %s, %s, %s", i,
                        rs.getLong("id"), rs.getString("username"), rs.getString("email"), rs.getString("phone")));
            }
        }
    }
}

