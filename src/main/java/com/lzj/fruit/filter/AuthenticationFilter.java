package com.lzj.fruit.filter;

import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private final String[] notRequiringLoginUrls;

    public AuthenticationFilter() {
        try (InputStream in = UserDaoImpl.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(in); // 加载properties文件格式的输入流
            String loginRequiredUrls = properties.getProperty("not.requiring.login.urls");
            this.notRequiringLoginUrls = loginRequiredUrls.split("\\s*,\\s*");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        // 1 检查当前请求的url是否需要登录
        String url = httpServletRequest.getRequestURL().toString();
        // 不需要登陆的页面，检查
        String contextPath = httpServletRequest.getContextPath();
        if (url.endsWith(contextPath + "/")) { // 首页不需要登录
            // 不需要登陆，返回
            chain.doFilter(request, response);
            return;
        }
        for (String notRequiringLoginUrl : notRequiringLoginUrls) {
            if(url.contains(contextPath + notRequiringLoginUrl)) {
                // 不需要登陆，返回
                chain.doFilter(request, response);
                return;
            }
        }
        // 2 如果需要登录，检查user是否存在
        if(null == user) {
            // 不能是httpServletResponse.sendRedirect，否则会陷入死循环
//            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            httpServletResponse.sendRedirect("login?redirect=" + url);
            request.getRequestDispatcher("login?redirect=" + url).forward(request, response);
        }
    }
}
