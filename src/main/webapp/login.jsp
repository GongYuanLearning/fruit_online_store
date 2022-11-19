<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %><%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@include file="common/ctx.jsp" %>

<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if (Objects.isNull(errors)) {
        errors = new ArrayList<>();
    }
%>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>天天生鲜-登录</title>
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<div class="login_top clearfix">
    <a href="${ctx}/login" class="login_logo"><img src="images/logo02.png"></a>
</div>

<div class="login_form_bg">
    <div class="login_form_wrap clearfix">
        <div class="login_banner fl"></div>
        <div class="slogan fl">日夜兼程 · 急速送达</div>
        <div class="login_form fr">
            <div class="login_title clearfix">
                <h1>用户登录</h1>
                <a href="${ctx}/register">立即注册</a>
            </div>
            <div class="errors">
                <c:forEach items="${errors}" var="error">
                    <div>${error}</div>
                </c:forEach>
            </div>
            <div class="form_input">

                <form action="${ctx}/login" method="post">
                    <input type="text" name="username" value="${username}" class="name_input" placeholder="请输入用户名">
                    <div class="user_error">输入错误</div>
                    <input type="password" name="pwd" class="pass_input" placeholder="请输入密码">
                    <div class="pwd_error">输入错误</div>
                    <div class="more_input clearfix">
                        <input type="checkbox" name="rememberMe">
                        <label>7天内免登录</label>
                        <a href="#">忘记密码</a>
                    </div>
                    <input type="submit" name="" value="登录" class="input_submit">
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

</body>
</html>