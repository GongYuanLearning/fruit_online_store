package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.ProductCategoryDao;
import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.util.DBUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDaoImpl implements ProductCategoryDao {
    @Override
    public ProductCategory getById(long id) throws PersistentException {
        String sql = "select * from product_category where id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new NotFoundException(String.format("No Product Category with id %d", id));
            }

            ProductCategory entity = new ProductCategory();
            entity.setId(id);
            entity.setName(rs.getString("name"));

            return entity;
        } catch (Exception e) {
            throw new PersistentException(String.format("Error occurs while getting product category by id %d", id), e);
        }
    }

    @Override
    public List<ProductCategory> getAll() throws PersistentException {
        String sql = "select * from product_category";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<ProductCategory> categories = new ArrayList<>();
            while(rs.next()) {
                ProductCategory entity = new ProductCategory();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));

                categories.add(entity);
            }

            return categories;
        } catch (Exception e) {
            throw new PersistentException("Error occurs while getting product categories", e);
        }
    }
}

