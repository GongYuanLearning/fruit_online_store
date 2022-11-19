<%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common/ctx.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <title>天天生鲜-购物车</title>
  <link rel="stylesheet" type="text/css" href="css/reset.css">
  <link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<%@include file="common/top.jsp" %>
<%@include file="common/searchBar.jsp" %>
<div class="total_count">全部商品<em>${cartItems.size()}</em>件</div>
<ul class="cart_list_th clearfix">
  <li class="col01">商品名称</li>
  <li class="col02">商品单位</li>
  <li class="col03">商品价格</li>
  <li class="col04">数量</li>
  <li class="col05">小计</li>
  <li class="col06">操作</li>
</ul>

<c:forEach items="${cartItems}" var="item">
  <ul class="cart_list_td clearfix">
    <li class="col01"><input type="checkbox" name="" checked></li>
    <li class="col02"><img src="${item.product.imagePath}"></li>
    <li class="col03">${item.product.name}<br><em>${item.product.price}元/${item.product.unit}</em></li>
    <li class="col04">${item.product.unit}</li>
    <li class="col05">${item.product.price}元</li>
    <li class="col06">
      <div class="num_add">
        <a href="javascript:;" class="add fl">+</a>
        <input type="text" class="num_show fl" value="${item.count}">
        <a href="javascript:;" class="minus fl">-</a>
      </div>
    </li>
    <li class="col07">${item.totalAmount}元</li>
    <li class="col08"><a href="javascript:;">删除</a></li>
  </ul>
</c:forEach>
<ul class="settlements">
  <li class="col01"><input type="checkbox" name="" checked=""></li>
  <li class="col02">全选</li>
  <li class="col03">合计(不含运费)：<span>¥</span><em>${totalAmount}</em><br>共计<b>${cartItems.size()}</b>件商品</li>
  <li class="col04"><a href="place_order.html">去结算</a></li>
</ul>

<%@include file="common/footer.jsp" %>

</body>
</html>
