package com.lzj.fruit.service.impl;

import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dao.impl.ProductDaoImpl;
import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.service.ProductService;
import com.lzj.fruit.util.DBUtil;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public void addProducts(List<ProductDto> products) throws Exception {
        this.productDao.addProducts(products);
    }
}
