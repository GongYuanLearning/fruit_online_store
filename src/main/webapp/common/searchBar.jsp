<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<div class="search_bar clearfix">
    <a href="index.jsp" class="logo fl"><img src="images/logo.png"></a>
    <div class="search_con fl">
        <input type="text" class="input_text fl" name="" placeholder="搜索商品">
        <input type="button" class="input_btn fr" name="" value="搜索">
    </div>
    <div class="guest_cart fr">
        <a href="${ctx}/cart" class="cart_name fl">我的购物车</a>
        <div class="goods_count fl" id="show_count">1</div>
    </div>
</div>

