package com.lzj.fruit.service;

import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.dto.SearchResult;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws Exception;

     void addProducts(List<ProductDto> products) throws Exception;

    List<Product> getProductsByCategory(long categoryId, int pageSize) throws Exception;
}
