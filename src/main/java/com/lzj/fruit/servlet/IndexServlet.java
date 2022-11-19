package com.lzj.fruit.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // cookie头中是否有用户相关信息的cookie：username_cookie
        Cookie[] cookies = request.getCookies();
        String username = null;
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("username_cookie".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
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
}

