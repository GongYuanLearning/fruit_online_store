package com.lzj.fruit.filter;

import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.service.UserService;
import com.lzj.fruit.service.impl.UserServiceImpl;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@WebFilter(filterName = "RememberMeFilter")
public class RememberMeFilter implements Filter {
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.userService = new UserServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        if (Objects.nonNull(session) &&
                Objects.nonNull(session.getAttribute("user"))) { // session已经有user, 用户已登录
            chain.doFilter(request, response);
            return;
        }
        // cookie中寻找user相关的信息
        Cookie[] cookies = httpServletRequest.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    String username = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.name());
                    try {
                        User user = this.userService.getByUsername(username);
                        httpServletRequest.getSession().setAttribute("user", user);
                    } catch (Exception e) {
                        throw new ServletException(e);
                    }
                    break;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
