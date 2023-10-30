package com.lzj.fruit.entity;

import lombok.Data;

@Data
public class ProductCategory {
    private long id;
    private String name;
    private ProductCategory parentCategory;
}

