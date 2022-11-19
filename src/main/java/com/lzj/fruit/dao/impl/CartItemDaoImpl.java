package com.lzj.fruit.dao.impl;

import com.lzj.fruit.dao.CartItemDao;
import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.entity.Product;
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

public class CartItemDaoImpl implements CartItemDao {
    private ProductDao productDao;

    public CartItemDaoImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public CartItem getCartItemById(long id) throws PersistentException {
        String sql = "select * from cart_item where id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new NotFoundException(String.format("No cart item with id %d", id));
            }

            CartItem entity = new CartItem();
            entity.setId(id);
            entity.setProduct(productDao.getProductById(rs.getLong("product_id")));
            entity.setCount(rs.getInt("count"));
            entity.setTotalAmount(rs.getBigDecimal("total_amount"));

            return entity;
        } catch (Exception e) {
            throw new PersistentException(String.format("Error occurs while getting cart item by id %d", id), e);
        }
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws PersistentException {
        String sql = "select * from cart_item where user_id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            List<CartItem> cartItems = new ArrayList<>();
            while(rs.next()) {
                CartItem entity = new CartItem();
                entity.setId(rs.getLong("id"));
                Product product = productDao.getProductById(rs.getLong("product_id"));
                entity.setProduct(product);
                entity.setCount(rs.getInt("count"));
                entity.setTotalAmount(rs.getBigDecimal("total_amount"));

                cartItems.add(entity);
            }
            return cartItems;
        } catch (Exception e) {
            throw new PersistentException(String.format("Error occurs while getting cart items by user id %d", userId), e);
        }
    }

    @Override
    public void addCartItem(CartItem cartItem, long userId) throws PersistentException {
        String sql = "INSERT INTO cart_item(product_id, user_id, count, total_amount) VALUES (?,?,?,?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cartItem.getProduct().getId());
            pstmt.setLong(2, userId);
            pstmt.setInt(3, cartItem.getCount());
            pstmt.setBigDecimal(4, cartItem.getTotalAmount());
            int count = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new PersistentException("Can not add cart item", e);
        }
    }

    @Override
    public CartItem deleteCartItem(long carItemId) {
        return null;
    }

    @Override
    public CartItem modifyCartItem(long carItemId, CartItem cartItem) throws PersistentException {
        CartItem cartItemToUpdate = this.getCartItemById(carItemId);
        String sql = "UPDATE cart_item SET count=?, total_amount=? WHERE id=?";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cartItem.getCount());
            pstmt.setBigDecimal(2, cartItem.getTotalAmount());
            pstmt.setLong(3, carItemId);
            int count = pstmt.executeUpdate();
        } catch (Exception e) {
            throw new PersistentException("Can not update cart item", e);
        }
        return cartItem;
    }
}

