<%@ page import="java.util.List" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.lzj.fruit.util.ServiceUtil" %>
<%@ page import="com.lzj.fruit.entity.User" %>
<%@ page import="com.lzj.fruit.service.UserService" %>
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
    String username = ServiceUtil.decode(request.getParameter("username"));
    String pwd = request.getParameter("pwd");
    User user = new User(username, pwd, "");
    pageContext.setAttribute("user", user, PageContext.PAGE_SCOPE); // 等同于下面的jsp:useBean申明的id为user的java bean
    UserService userService = new UserService(); // 实例化JavaBean UserService
    if (userService.login(username, pwd)) {
%> 你好，你已经登录成功！
<%--<jsp:forward page="index.jsp"></jsp:forward>--%>
<%--    <jsp:useBean id="user" class="com.lzj.fruit.entity.User" scope="page" />--%>
<%-- 参数的名称要跟java bean 对象的属性名完全一样，才可以绑定 --%>
<%--    <jsp:setProperty name="user" property="*" />--%>
用户名：<%=user.getUsername()%>
<%--<jsp:getProperty name="user" property="username" />--%>
<br />
密码：<%=user.getPassword()%>
<%--<jsp:getProperty name="user" property="password" />--%>
<br />
邮箱：<%=user.getEmail()%>
<%--<jsp:getProperty name="user" property="email" />--%>
<%
} else {
    List<String> errors = new ArrayList<>();
    errors.add("用户名和密码不匹配！");
%>
<jsp:forward page="login.jsp"></jsp:forward>
<%
    }
%>
</body>
</html>
