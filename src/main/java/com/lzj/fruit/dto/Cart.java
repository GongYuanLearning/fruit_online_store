package com.lzj.fruit.dto;

import com.lzj.fruit.entity.CartItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items = new ArrayList<>();
}
