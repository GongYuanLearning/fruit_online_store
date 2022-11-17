package com.lzj.fruit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * User类对应数据库表user
 */
@Data
@NoArgsConstructor // 生成无惨构造方法
@AllArgsConstructor // 生成带所有属性对应参数的构造方法
public class User {
    private Long id;
    private String username;
    private String password;
    private String pwdHash;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
