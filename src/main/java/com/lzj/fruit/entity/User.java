package com.lzj.fruit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 生成无惨构造方法
@AllArgsConstructor // 生成带所有属性对应参数的构造方法
public class User {
    private String username;
    private String password;
    private String email;
}
