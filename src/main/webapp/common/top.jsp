<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header_con">
  <div class="header">
    <div class="welcome fl">欢迎来到天天生鲜!</div>
    <div class="fr">
      <div class="login_info fl">
        欢迎您：<em>${user.username}</em>
      </div>
      <div class="login_btn fl">
        <c:choose>
          <c:when test="${not empty user}">
            <a href="${ctx}/logout">退出</a>
          </c:when>
          <c:otherwise>
            <a href="${ctx}/login">登录</a>
          </c:otherwise>
        </c:choose>
        <span>|</span>
        <a href="${ctx}/register">注册</a>
      </div>
      <div class="user_link fl">
        <span>|</span>
        <a href="user_center_info.html">用户中心</a>
        <span>|</span>
        <a href="cart.html">我的购物车</a>
        <span>|</span>
        <a href="user_center_order.html">我的订单</a>
      </div>
    </div>
  </div>
</div>

