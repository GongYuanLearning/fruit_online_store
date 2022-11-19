package com.lzj.fruit.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private long id;
    private String name;
    private String imagePath;
    private String description;
    private String detail;
    private BigDecimal price;
    private String unit;
    private ProductCategory category;
}
