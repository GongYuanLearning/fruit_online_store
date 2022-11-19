package com.lzj.fruit.servlet;


import com.lzj.fruit.entity.Product;
import com.lzj.fruit.service.ProductService;
import com.lzj.fruit.service.impl.ProductServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class ProductDetailServlet extends HttpServlet {
    private ProductService productService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            long id = Long.parseLong(idStr);
            Product product = productService.getProductById(id);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/jsp/detail.jsp").forward(request, response);
        } catch( NumberFormatException e) {
            throw new ServletException("id必须为整数!");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
