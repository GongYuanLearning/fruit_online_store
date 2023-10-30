package com.lzj.fruit.dao;

import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.exception.PersistentException;

import java.util.List;

public interface CartItemDao {
    CartItem getCartItemById(long id) throws PersistentException;
    List<CartItem> getCartItems(long userId) throws PersistentException;
    void addCartItem(CartItem cartItem, long userId) throws PersistentException;
    CartItem deleteCartItem(long carItemId);
    CartItem modifyCartItem(long carItemId, CartItem cartItem) throws PersistentException;
}
