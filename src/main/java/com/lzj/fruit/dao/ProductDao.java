package com.lzj.fruit.dao;

import com.lzj.fruit.dto.ProductCriteria;
import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface ProductDao {
    Product getProductById(long id) throws PersistentException;
    List<Product> searchProducts(ProductCriteria criteria) throws PersistentException;
    List<Product> recommendProducts(int count) throws PersistentException;

    void addProducts(List<ProductDto> products) throws PersistentException;
}