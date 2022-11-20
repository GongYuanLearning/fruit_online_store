package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.ProductCategoryDao;
import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dto.ProductCriteria;
import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDaoImpl implements ProductDao {
    private static final String ADD_PRODUCT_SQL = "INSERT INTO product(name, imagePath, description, detail, price, unit, category_id) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private ProductCategoryDao productCategoryDao;

    public ProductDaoImpl() {
        this.productCategoryDao = new ProductCategoryDaoImpl();
    }

    public void addProducts(List<ProductDto> products) throws PersistentException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(ADD_PRODUCT_SQL);
        ) {
            for (ProductDto product : products) {
                pstmt.setString(1, product.getName());
                pstmt.setString(2, product.getImagePath());
                pstmt.setString(3, product.getDescription());
                pstmt.setString(4, product.getDetail());
                pstmt.setBigDecimal(5, product.getPrice());
                pstmt.setString(6, product.getUnit());
                pstmt.setLong(7, product.getCategoryId());
                pstmt.addBatch(); // 将product添加到批处理
            }
            pstmt.executeBatch(); // 执行批处理
        } catch (Exception e) {
            throw new PersistentException(e);
        }
    }

    @Override
    public Product getProductById(long id) throws PersistentException {
        String sql = "select * from product where id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                throw new NotFoundException(String.format("No Product with id %d", id));
            }

            Product entity = new Product();
            entity.setId(id);
            entity.setName(rs.getString("name"));
            entity.setImagePath(rs.getString("imagePath"));
            entity.setPrice(rs.getBigDecimal("price"));
            entity.setUnit(rs.getString("unit"));
            entity.setDescription(rs.getString("description"));
            entity.setDetail(rs.getString("detail"));
            ProductCategory category = this.productCategoryDao.getById(rs.getLong("category_id"));
            entity.setCategory(category);

            return entity;
        } catch (Exception e) {
            throw new PersistentException(String.format("Error occurs while getting product by id %d", id), e);
        }
    }

    @Override
    public List<Product> searchProducts(ProductCriteria criteria) throws PersistentException {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        if (criteria.getCategoryId() > 0) {
            sql.append(String.format(" AND category_id=%d", criteria.getCategoryId()));
        }
        if (Objects.nonNull(criteria.getKeyword()) && !criteria.getKeyword().isEmpty()) {
            sql.append(String.format(" AND (name LIKE %%%s%% OR description LIKE %%%s%%)", criteria.getKeyword(), criteria.getKeyword()));
        }
        if (Objects.nonNull(criteria.getSortBy()) && !criteria.getSortBy().isEmpty()) {
            sql.append(String.format(" order by %s ", criteria.getKeyword()));
            if ("desc".equalsIgnoreCase(criteria.getSortDirection())) {
                sql.append(" desc ");
            }
        }
        sql.append(String.format(" limit %d, %d", (criteria.getPageNumber() - 1) * criteria.getPageSize(), criteria.getPageSize()));

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            ResultSet rs = pstmt.executeQuery();

            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product entity = new Product();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                entity.setImagePath(rs.getString("imagePath"));
                entity.setPrice(rs.getBigDecimal("price"));
                entity.setUnit(rs.getString("unit"));
                entity.setDescription(rs.getString("description"));
                entity.setDetail(rs.getString("detail"));
                ProductCategory category = this.productCategoryDao.getById(rs.getLong("category_id"));
                entity.setCategory(category);

                products.add(entity);
            }

            return products;
        } catch (Exception e) {
            throw new PersistentException("Error occurs while recommending products", e);
        }
    }

    @Override
    public List<Product> recommendProducts(int count) throws PersistentException {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setPageSize(count);
        criteria.setSortBy("on_store_time");
        criteria.setSortDirection("desc");
        return searchProducts(criteria);
    }
}
