package com.lzj.fruit.service.impl;

import com.lzj.fruit.dao.CartItemDao;
import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dao.impl.CartItemDaoImpl;
import com.lzj.fruit.dao.impl.ProductDaoImpl;
import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.service.CartItemService;

import java.util.List;

public class CartItemServiceImpl implements CartItemService {
    private CartItemDao cartItemDao;

    public CartItemServiceImpl() {
        this.cartItemDao = new CartItemDaoImpl(new ProductDaoImpl());
    }

    @Override
    public CartItem getCartItemById(long id) throws Exception {
        return null;
    }

    @Override
    public List<CartItem> getCartItems(long userId) throws Exception {
        return this.cartItemDao.getCartItems(userId);
    }

    @Override
    public void addCartItem(CartItem cartItem, long userId) throws Exception {
        this.cartItemDao.addCartItem(cartItem, userId);
    }

    @Override
    public CartItem deleteCartItem(long carItemId) {
        return null;
    }

    @Override
    public CartItem modifyCartItem(long carItemId, CartItem cartItem) throws Exception {
        return this.cartItemDao.modifyCartItem(carItemId, cartItem);
    }
}
