package com.lzj.fruit.servlet;

import com.lzj.fruit.dao.ProductCategoryDao;
import com.lzj.fruit.entity.Product;
import com.lzj.fruit.entity.ProductCategory;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.exception.PersistentException;
import com.lzj.fruit.service.ProductCategoryService;
import com.lzj.fruit.service.ProductService;
import com.lzj.fruit.service.impl.ProductCategoryServiceImpl;
import com.lzj.fruit.service.impl.ProductServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private ProductService productService;
    private ProductCategoryService productCategoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductServiceImpl();
        this.productCategoryService = new ProductCategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ProductCategory> categories = this.productCategoryService.getAll();
            request.setAttribute("categories", categories);
            Map<Long, List<Product>> productsByCategory = new HashMap<>();
            for (ProductCategory cat : categories) {
                productsByCategory.put(cat.getId(), this.productService.getProductsByCategory(cat.getId(), 4));
            }
            request.setAttribute("productsByCategory", productsByCategory);
        } catch (Exception e) {
            throw new ServletException(e);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
