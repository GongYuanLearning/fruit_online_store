package com.lzj.fruit.dao;

import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface ProductCategoryDao {
    ProductCategory getById(long id) throws NotFoundException, PersistentException;
    List<ProductCategory> getAll() throws PersistentException;
}
