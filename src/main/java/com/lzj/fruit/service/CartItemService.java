package com.lzj.fruit.service;

import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.exception.PersistentException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface CartItemService {
    CartItem getCartItemById(long id) throws Exception;
    List<CartItem> getCartItems(long userId) throws Exception;
    void addCartItem(CartItem cartItem, long userId) throws Exception;
    CartItem deleteCartItem(long carItemId);
    CartItem modifyCartItem(long carItemId, CartItem cartItem) throws Exception;
}
