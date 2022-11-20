package com.lzj.fruit.service;

import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory getById(long id) throws PersistentException;
    List<ProductCategory> getAll() throws PersistentException;
}
