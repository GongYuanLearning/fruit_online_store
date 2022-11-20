package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.ProductCategoryDao;
import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dto.ProductCriteria;
import com.lzj.fruit.dto.ProductDto;
import com.lzj.fruit.dto.SearchResult;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.NotFoundException;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.util.DBUtil;
import com.lzj.fruit.util.ServiceUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.lzj.fruit.dao.impl.UserDaoImpl.DELETE_USER_BY_ID_SQL;

public class ProductDaoImpl implements ProductDao {
    private static final String ADD_PRODUCT_SQL = "INSERT INTO product(name, imagePath, description, detail, price, unit, category_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET %s WHERE id=?";
    public static final String DELETE_PRODUCT_BY_ID_SQL = "DELETE FROM product WHERE id=?";

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
    public Product create(ProductDto product) throws Exception {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ADD_PRODUCT_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getImagePath());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getDetail());
            pstmt.setBigDecimal(5, product.getPrice());
            pstmt.setString(6, product.getUnit());
            pstmt.setLong(7, product.getCategoryId());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys(); // 获取生成的主键
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getLong(1)); // 只能用columnIndex参数
            }
            return getProductById(product.getId());
        }
    }

    @Override
    public Product update(ProductDto product) throws Exception {
        Product dbProduct = getProductById(product.getId());

        List<String> segments = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        if (ServiceUtil.notNullOrEmpty(product.getName())) {
            segments.add("name=?");
            fields.add("name");
        }
        if (product.getPrice() != null) {
            segments.add("price=?");
            fields.add("price");
        }
        String updateSegment = String.join(",", segments);
        String sql = String.format(UPDATE_PRODUCT_SQL, updateSegment);

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            if (ServiceUtil.notNullOrEmpty(product.getName())) {
                int idx = fields.indexOf("name");
                pstmt.setString(idx + 1, product.getName());
            }
            if (product.getPrice() != null) {
                int idx = fields.indexOf("price");
                pstmt.setBigDecimal(idx + 1, product.getPrice());
            }
            pstmt.setLong(fields.size() + 1, product.getId());

            pstmt.executeUpdate();
        }
        return dbProduct;
    }

    @Override
    public void delete(long id) throws Exception {
        getProductById(id);
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_PRODUCT_BY_ID_SQL)
        ) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
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

            Product entity = extractProduct(rs);

            return entity;
        } catch (Exception e) {
            throw new PersistentException(String.format("Error occurs while getting product by id %d", id), e);
        }
    }

    private Product extractProduct(ResultSet rs) throws SQLException, PersistentException {
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
        return entity;
    }

    @Override
    public SearchResult<Product> searchProducts(ProductCriteria criteria) throws PersistentException {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE 1=1");
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM product WHERE 1=1");
        StringBuilder conditionSegment = new StringBuilder();
        if (criteria.getCategoryId() > 0) {
            conditionSegment.append(String.format(" AND category_id=%d", criteria.getCategoryId()));
        }
        if (Objects.nonNull(criteria.getKeyword()) && !criteria.getKeyword().isEmpty()) {
            conditionSegment.append(String.format(" AND (name LIKE %%%s%% OR description LIKE %%%s%%)", criteria.getKeyword(), criteria.getKeyword()));
        }
        if (Objects.nonNull(criteria.getSortBy()) && !criteria.getSortBy().isEmpty()) {
            conditionSegment.append(String.format(" order by %s ", criteria.getSortBy()));
            if ("desc".equalsIgnoreCase(criteria.getSortDirection())) {
                conditionSegment.append(" desc ");
            }
        }

        sql.append(conditionSegment);
        countSql.append(conditionSegment);

        if (criteria.getPageSize() <= 0) {
            criteria.setPageSize(ProductCriteria.DEFAULT_PAGE_SIZE);
        }
        if (criteria.getPageNumber() < 0) {
            criteria.setPageNumber(0);
        }

        sql.append(String.format(" limit %d, %d", criteria.getPageNumber() * criteria.getPageSize(), criteria.getPageSize()));

        SearchResult result = new SearchResult();
        result.setPageSize(criteria.getPageSize());
        result.setPageNumber(criteria.getPageNumber());
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString());
             PreparedStatement countPstmt = conn.prepareStatement(countSql.toString());) {
            ResultSet rs = pstmt.executeQuery();
            ResultSet countRs = countPstmt.executeQuery(); // 获取数量总记录数量

            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                products.add(extractProduct(rs));
            }

            countRs.next();
            long total = countRs.getLong(1);
            result.setTotal(total);

            long totalPages = total / criteria.getPageSize();
            totalPages = totalPages * criteria.getPageSize() < total ? totalPages + 1 : totalPages;
            result.setTotalPages(totalPages);

            result.setData(products);
            return result;
        } catch (Exception e) {
            throw new PersistentException("Error occurs while searching products", e);
        }
    }

    @Override
    public List<Product> recommendProducts(int count) throws PersistentException {
        ProductCriteria criteria = new ProductCriteria();
        criteria.setPageSize(count);
        criteria.setSortBy("on_store_time");
        criteria.setSortDirection("desc");
        SearchResult<Product> searchResult = searchProducts(criteria);
        return searchResult.getData();
    }
}
