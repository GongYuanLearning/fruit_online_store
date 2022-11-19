package com.lzj.fruit.servlet;

import com.lzj.fruit.dao.CartItemDao;
import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dao.impl.CartItemDaoImpl;
import com.lzj.fruit.dao.impl.ProductCategoryDaoImpl;
import com.lzj.fruit.dao.impl.ProductDaoImpl;
import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.dto.Cart;
import com.lzj.fruit.entity.CartItem;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.service.CartItemService;
import com.lzj.fruit.service.impl.CartItemServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@WebServlet("/showCart")
public class ShowCartServlet extends HttpServlet {
    private CartItemService cartItemService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartItemService = new CartItemServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");
        if(Objects.isNull(user)) {
            resp.sendRedirect("login");
            return;
        }
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
        req.getRequestDispatcher("jsp/cart.jsp").forward(req, resp);
    }
}
