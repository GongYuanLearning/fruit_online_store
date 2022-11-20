package com.lzj.fruit.servlet;

import com.lzj.fruit.dao.CartItemDao;
import com.lzj.fruit.dao.ProductDao;
import com.lzj.fruit.dao.impl.CartItemDaoImpl;
import com.lzj.fruit.dao.impl.ProductCategoryDaoImpl;
import com.lzj.fruit.dao.impl.ProductDaoImpl;
import com.lzj.fruit.dto.Cart;
import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.service.CartItemService;
import com.lzj.fruit.service.ProductService;
import com.lzj.fruit.service.impl.CartItemServiceImpl;
import com.lzj.fruit.service.impl.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartItemService cartItemService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductServiceImpl();
        this.cartItemService = new CartItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");
        if(Objects.isNull(cart)) {
            try {
                List<CartItem> cartItems = cartItemService.getCartItems(user.getId());
                cart = new Cart();
                cart.setItems(cartItems);
                session.setAttribute("cart", cart);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            totalAmount.add(cartItem.getTotalAmount());
        }
        req.setAttribute("cartItems", cart.getItems());
        req.setAttribute("totalAmount", totalAmount);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            // 从数据库查找product
            Product product = getProduct(id);

            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            long userId = user.getId();
            Cart cart = (Cart) session.getAttribute("cart");// 从session获取购物车cart
            if(null == cart) { // 第一次访问，购物车cart不存在的情况
                cart = new Cart();
                // 从数据库获取用户的购物车
                List<CartItem> cartItems = this.cartItemService.getCartItems(userId);
                cart.setItems(cartItems);
                session.setAttribute("cart", cart);
            }
            boolean exist = false; // 购物车中是否已经有该产品
            for (CartItem item : cart.getItems()) {
                if(item.getProduct().getId() == product.getId()) {
                    item.setCount(item.getCount() + 1);
                    item.setTotalAmount(item.getTotalAmount().add(product.getPrice()));
                    exist = true;
                    // 更新购物车项目
                    this.cartItemService.modifyCartItem(item.getId(), item);
                    break;
                }
            }
            if(!exist) {
                CartItem item = new CartItem();
                item.setProduct(product);
                item.setCount(1);
                item.setTotalAmount(product.getPrice());
                // 插入购物车项目
                this.cartItemService.addCartItem(item, userId);
                cart.getItems().add(item);
            }

            resp.sendRedirect("cart");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Product getProduct(String id) throws Exception {
        return productService.getProductById(Long.parseLong(id));
    }
}
