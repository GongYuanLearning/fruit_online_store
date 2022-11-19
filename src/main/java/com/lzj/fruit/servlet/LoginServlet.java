package com.lzj.fruit.servlet;

import com.lzj.fruit.service.UserService;
import com.lzj.fruit.service.impl.UserServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        request.setCharacterEncoding(encoding); // 在调用具体方法前，设置字符编码
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String rememberMe = request.getParameter("rememberMe");
        try {
            boolean res = userService.login(username, pwd);
            if (res) {
                // 返回用户相关信息的cookie：username_cookie=用户名
                if ("on".equals(rememberMe)) {
                    Cookie cookie = new Cookie("username_cookie", username);
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                response.sendRedirect("index.jsp");
            } else {
                List<String> errors = new ArrayList<>(); // 存储错误消息的list
                errors.add("用户名和密码不匹配！");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

