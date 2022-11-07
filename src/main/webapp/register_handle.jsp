<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // 从request中获取参数username，pwd
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    if ("user".equals(username) && "password".equals(pwd)) { // 用户名为user，密码为password，让页面跳转到login页面
%>
<jsp:forward page="login.jsp">
    <jsp:param name="username" value="<%= username %>"/>
    <jsp:param name="pwd" value="<%= pwd %>"/>
</jsp:forward>
<%
} else  {
    List<String> errors = new ArrayList<>(); // 存储错误消息的list
    errors.add("无效的用户名和密码");
    request.setAttribute("errors", errors);
%>
<jsp:forward page="register.jsp"></jsp:forward>
<%
    }
%>
