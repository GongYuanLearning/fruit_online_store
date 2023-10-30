package com.lzj.fruit.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * 查询User的条件对象
 */
@Data
public class UserCriteria {
    public static final int DEFAULT_PAGE_SIZE = 2;
    public static final String ASC = "asc";
    // 表示每次查询的最多获取的数据记录条数
    private int pageSize = DEFAULT_PAGE_SIZE;
    // 当前查询的页码，从0开始
    private int pageNumber;
    private String sortBy;
    private String sortDirection = ASC; // asc, desc
    private String username;
    private String email;
    private LocalDate birthday;
}
