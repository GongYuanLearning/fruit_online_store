package com.lzj.fruit.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "DateTimeServlet", value = "/dateTime")
public class DateTimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"));
        response.getWriter().print("<!DOCTYPE html>");
        response.getWriter().print("<html>");
        response.getWriter().print("    <head>");
        response.getWriter().print("        <title>");
        response.getWriter().print("            当前时间：");
        response.getWriter().print("        </title>");
        response.getWriter().print("    </head>");
        response.getWriter().print("    <body>");
        response.getWriter().print(String.format("        当前时间是：%s", formattedDateTime));
        response.getWriter().print("    </body>");
        response.getWriter().print("</html>");
    }
}
