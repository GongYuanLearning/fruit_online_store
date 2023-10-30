package com.lzj.fruit.servlet;

import com.lzj.fruit.entity.User;
import com.lzj.fruit.service.UserService;
import com.lzj.fruit.service.impl.UserServiceImpl;
import com.lzj.fruit.util.ServiceUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "LoginServlet",
        value = "/login",
        initParams = {
                @WebInitParam(name="encoding", value="UTF-8")
        })
public class LoginServlet extends HttpServlet {
    private String encoding;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.encoding = config.getInitParameter("encoding");
        userService = new UserServiceImpl();
    }

    /**
     * 处理显示登录界面的请求。
     * @param request   an {@link HttpServletRequest} object that
     *                  contains the request the client has made
     *                  of the servlet
     *
     * @param response  an {@link HttpServletResponse} object that
     *                  contains the response the servlet sends
     *                  to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String rememberMe = request.getParameter("rememberMe");
        try {
            User user = userService.login(username, pwd);
            if (Objects.nonNull(user)) {
                // 登录后，在session中保存username
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("user", user);
                // 返回用户相关信息的cookie：username_cookie=用户名
                if ("on".equals(rememberMe)) {
                    Cookie cookie = new Cookie("rememberMe",
                            URLEncoder.encode(username, StandardCharsets.UTF_8.name()));
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                response.sendRedirect("index");
            } else {
                List<String> errors = new ArrayList<>(); // 存储错误消息的list
                errors.add("用户名和密码不匹配！");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
