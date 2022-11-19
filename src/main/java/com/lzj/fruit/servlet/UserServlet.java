package com.lzj.fruit.servlet;

import com.lzj.fruit.dao.UserDao;
import com.lzj.fruit.dao.impl.UserDaoImpl;
import com.lzj.fruit.entity.User;
import com.lzj.fruit.util.ServiceUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao = new UserDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (ServiceUtil.notNullOrEmpty(username)) {
            try {
                User user = userDao.getByUsernameOrEmail(username);
                //language=JSON
                String res = "{\n" +
                        "  \"id\": %d,\n" +
                        "  \"username\": \"%s\",\n" +
                        "  \"email\": \"%s\",\n" +
                        "  \"phone\": \"%s\",\n" +
                        "  \"name\": \"张三\",\n" +
                        "  \"birthDate\": \"%s\"\n" +
                        "}";
                res = String.format(res, user.getId(), user.getUsername(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//                resp.setContentType("application/json");
                // 默认是ISO-8859-1编码，Chrome浏览器默认现在是UTF-8
                // resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json;charset=UTF-8");
                resp.getWriter().print(res);
            } catch (Exception e) {
                resp.sendError(500, "内部错误");
            }
        } else {
            resp.sendError(400, "请提供username");
        }
    }
}

