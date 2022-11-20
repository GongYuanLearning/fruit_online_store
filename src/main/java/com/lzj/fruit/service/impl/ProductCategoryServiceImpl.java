package com.lzj.fruit.service.impl;

import com.lzj.fruit.dao.ProductCategoryDao;
import com.lzj.fruit.dao.impl.ProductCategoryDaoImpl;
import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.service.ProductCategoryService;

import java.util.List;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryDao productCategoryDao;

    public ProductCategoryServiceImpl() {
        this.productCategoryDao = new ProductCategoryDaoImpl();
    }

    @Override
    public ProductCategory getById(long id) throws PersistentException {
        return this.productCategoryDao.getById(id);
    }

    @Override
    public List<ProductCategory> getAll() throws PersistentException {
        return this.productCategoryDao.getAll();
    }
}
