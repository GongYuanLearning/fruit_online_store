<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String username = request.getParameter("username");
    String pwd = request.getParameter("pwd");
    String cpwd = request.getParameter("cpwd");
    String email = request.getParameter("email");
    String agreement = request.getParameter("allow");
    if ("on".equals(agreement)
            && pwd.equals(cpwd)) { // 同意注册协议，并且密码和确认密码一样，允许注册
        session.setAttribute("username", username);
        session.setAttribute("pwd", pwd);
        session.setAttribute("email", email);
%>
<jsp:forward page="login.jsp"></jsp:forward>
<%
} else  {
    List<String> errors = new ArrayList<>(); // 存储错误消息的list
    if (!"on".equals(agreement)) {
        errors.add("必须同意协议才能注册！");
    }
    if (!pwd.equals(cpwd)) {
        errors.add("密码不匹配！");
    }
    request.setAttribute("errors", errors);
%>
<jsp:forward page="register.jsp"></jsp:forward>
<%
    }
%>
