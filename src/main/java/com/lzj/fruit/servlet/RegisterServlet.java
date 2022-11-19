package com.lzj.fruit.servlet;

import com.lzj.fruit.entity.User;
import com.lzj.fruit.service.UserService;
import com.lzj.fruit.service.impl.UserServiceImpl;
import com.lzj.fruit.util.ServiceUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 注册相关的请求处理。
 * http://localhost:8080/fruit/register
 */
@WebServlet(name = "RegisterServlet",
        value = "/register",
        loadOnStartup = 1,
        initParams = {
                @WebInitParam(name="encoding", value="UTF-8")
        }
)
public class RegisterServlet extends HttpServlet {
    private String encoding;
    private UserService userService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.encoding = config.getInitParameter("encoding");
        userService = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 选择视图jsp/register.jsp
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding(encoding); // 在调用具体方法前，设置字符编码
        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");
        String cpwd = request.getParameter("cpwd");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birthDateStr = request.getParameter("birthDate");
        LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String agreement = request.getParameter("allow");
        if (ServiceUtil.isValidUsername(username)
                && "on".equals(agreement)
                && pwd.equals(cpwd)) { // 用户名有效，同意注册协议，并且密码和确认密码一样，允许注册
            User user = new User(null, username, pwd, "", email, phone, birthDate);
            try {
                user = userService.register(user);
                request.setAttribute("username", username);
                // 转发请求到login.jsp
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.sendRedirect("login");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        } else {
            List<String> errors = new ArrayList<>(); // 存储错误消息的list
            if (!ServiceUtil.isValidUsername(username)) {
                errors.add("无效的用户名，用户名只能包含字母和数字");
            }
            if (!"on".equals(agreement)) {
                errors.add("必须同意协议才能注册！");
            }
            if (!pwd.equals(cpwd)) {
                errors.add("密码不匹配！");
            }
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("register").forward(request, response);
        }
    }
}
