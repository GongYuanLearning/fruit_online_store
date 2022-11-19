package com.lzj.fruit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 购物车item。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private long id;
    private Product product;
    private int count;
    private BigDecimal totalAmount;
}
