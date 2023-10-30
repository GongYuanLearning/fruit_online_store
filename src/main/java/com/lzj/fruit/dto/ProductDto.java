package com.lzj.fruit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private long id;
    private String name;
    private String imagePath;
    private String description;
    private String detail;
    private BigDecimal price;
    private String unit;
    private String category;
    private long categoryId;
}

