<%@ page import="java.util.List" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.ArrayList" %>
<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录处理</title>
</head>
<body>
<%
    // request.setCharacterEncoding(StandardCharsets.UTF_8.name());
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    String email = (String) session.getAttribute("email");
    if ("user".equals(username) && "password".equals(pwd)) {
%> <% out.write(username); %>: 你好，你已经登录成功！
<br/>
你的邮箱：<% out.write(email); %>
<%
} else {
    session.setAttribute("username", username);
    List<String> errors = new ArrayList<>(); // 存储错误消息的list
    errors.add("用户名和密码不匹配！");
    request.setAttribute("errors", errors);
%>
<jsp:forward page="login.jsp"></jsp:forward>
<%
    }
%>
</body>
</html>
