package com.lzj.fruit.service.impl;

import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dao.impl.ProductDaoImpl;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    public ProductServiceImpl() {
        this.productDao = new ProductDaoImpl();
    }

    @Override
    public Product getProductById(long id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Id必须大于0");
        }
        return productDao.getProductById(id);
    }
}
