package com.lzj.fruit.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResult<T> {
    private List<T> data; // 每次查询获取的数据列表
    private long total; // 一共多少条记录
    private long totalPages; // 一共多少页
    private int pageSize;
    private int pageNumber;
}
