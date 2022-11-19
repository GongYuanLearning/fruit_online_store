package com.lzj.fruit.service;

import com.lzj.fruit.entity.Product;
import com.lzj.fruit.exception.PersistentException;

public interface ProductService {
    Product getProductById(long id) throws Exception;
}
