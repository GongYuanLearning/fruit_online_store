<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoField" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
  List<String> errors = (List<String>) request.getAttribute("errors");
%>
<!doctype html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title>天天生鲜-注册</title>
  <link rel="stylesheet" type="text/css" href="css/reset.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <script type="text/javascript" src="js/jquery-3.6.1.js"></script>
  <script type="text/javascript" src="js/register.js"></script>
</head>
<body>
<div class="register_con">
  <div class="l_con fl">
    <a class="reg_logo"><img src="images/logo02.png"></a>
    <div class="reg_slogan">足不出户  ·  新鲜每一天</div>
    <div class="reg_banner"></div>
  </div>

  <div class="r_con fr">
    <div class="reg_title clearfix">
      <h1>用户注册</h1>
      <a href="#">登录</a>
    </div>
    <div class="reg_form clearfix">
      <div class="errors">
        <%
          if (Objects.nonNull(errors)) {
            for (String error : errors) {
        %>
        <div>${error}</div>
        <%
            }
          }
        %>
      </div>
      <form action="register_handle.jsp" method="post">
        <ul>
          <li>
            <label>用户名:</label>
            <input type="text" name="username" id="user_name">
            <span class="error_tip">提示信息</span>
          </li>
          <li>
            <label>密码:</label>
            <input type="password" name="pwd" id="pwd">
            <span class="error_tip">提示信息</span>
          </li>
          <li>
            <label>确认密码:</label>
            <input type="password" name="cpwd" id="cpwd">
            <span class="error_tip">提示信息</span>
          </li>
          <li>
            <label>邮箱:</label>
            <input type="text" name="email" id="email">
            <span class="error_tip">提示信息</span>
          </li>
          <li class="agreement">
            <input type="checkbox" name="allow" id="allow" checked="checked">
            <label>同意”天天生鲜用户使用协议“</label>
            <span class="error_tip2">提示信息</span>
          </li>
          <li class="reg_sub">
            <input type="submit" value="注 册" name="">
          </li>
        </ul>
      </form>
    </div>

  </div>

</div>

<%--  <%@include file="common/footer.jsp" %>--%>
  <jsp:include page="common/footer.jsp" />
</body>
</html>
