package com.lzj.fruit.servlet;

import com.lzj.fruit.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");// 用于获取登录后存储在session中的user对象
        if (Objects.isNull(username)) {
            // 7天之内免登录检查
            // cookie头中是否有用户相关信息的cookie：username_cookie
            Cookie[] cookies = request.getCookies();
            if (Objects.nonNull(cookies)) {
                for (Cookie cookie : cookies) {
                    if ("rememberMe".equals(cookie.getName())) {
                        username = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.name());
                        break;
                    }
                }
            }
        }
        // 如果有相关信息，代表用户已登录，显示
        if (Objects.nonNull(username)) {
            request.setAttribute("username", username);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            // 如果用户未登录，转到login请求；
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
