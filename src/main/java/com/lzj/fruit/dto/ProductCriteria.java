package com.lzj.fruit.dto;

import lombok.Data;

@Data
public class ProductCriteria {
    private long categoryId;
    private String keyword;
    private String sortBy;
    private String sortDirection;
    private int pageSize = 4;
    private int pageNumber = 1;
}
