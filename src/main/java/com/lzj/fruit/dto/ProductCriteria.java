package com.lzj.fruit.dto;

import lombok.Data;

@Data
public class ProductCriteria {
    public static final int DEFAULT_PAGE_SIZE = 4;
    private long categoryId;
    private String keyword;
    private String sortBy;
    private String sortDirection;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageNumber = 0;
}
