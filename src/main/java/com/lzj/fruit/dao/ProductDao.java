package com.lzj.fruit.dao;

import com.lzj.fruit.dto.ProductCriteria;
import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.dto.SearchResult;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface ProductDao {
    Product create(ProductDto product) throws Exception;

    Product update(ProductDto product) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    void delete(long id) throws Exception;

    Product getProductById(long id) throws PersistentException;

    SearchResult<Product> searchProducts(ProductCriteria criteria) throws PersistentException;

    List<Product> recommendProducts(int count) throws PersistentException;

    void addProducts(List<ProductDto> products) throws PersistentException;
}