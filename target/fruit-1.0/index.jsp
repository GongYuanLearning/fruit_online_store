<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.lzj.fruit.dto.ProductDto" %>
<%@ page import="java.math.BigDecimal" %><%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common/ctx.jsp" %>
<%
    List<String> categoriesClasses = new ArrayList<>();
    categoriesClasses.add("fruit");
    categoriesClasses.add("seafood");
    categoriesClasses.add("meet");
    categoriesClasses.add("egg");
    categoriesClasses.add("vegetables");
    categoriesClasses.add("ice");
    request.setAttribute("categoriesClasses", categoriesClasses);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>天天生鲜-首页</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/main.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.6.1.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${ctx}/js/slide.js"></script>
</head>
<body>
<%@include file="common/top.jsp" %>
<%@include file="common/searchBar.jsp" %>

<div class="navbar_con">
    <div class="navbar">
        <h1 class="fl">全部商品分类</h1>
        <ul class="navlist fl">
            <li><a href="">首页</a></li>
            <li class="interval">|</li>
            <li><a href="">手机生鲜</a></li>
            <li class="interval">|</li>
            <li><a href="">抽奖</a></li>
        </ul>
    </div>
</div>

<div class="center_con clearfix">
    <ul class="subnav fl">
        <c:forEach items="${categories}" var="category" varStatus="status">
            <li><a href="product/list?categoryId=${category.id}" class="${categoriesClasses[status.index]}">${category.name}</a></li>
        </c:forEach>
    </ul>
    <div class="slide fl">
        <ul class="slide_pics">
            <li><img src="${ctx}/images/slide.jpg" alt="幻灯片"></li>
            <li><img src="${ctx}/images/slide02.jpg" alt="幻灯片"></li>
            <li><img src="${ctx}/images/slide03.jpg" alt="幻灯片"></li>
            <li><img src="${ctx}/images/slide04.jpg" alt="幻灯片"></li>
        </ul>
        <div class="prev"></div>
        <div class="next"></div>
        <ul class="points"></ul>
    </div>
    <div class="adv fl">
        <a href="#"><img src="${ctx}/images/adv01.jpg"></a>
        <a href="#"><img src="${ctx}/images/adv02.jpg"></a>
    </div>
</div>

<c:forEach items="${categories}" var="category">
<div class="list_model">
    <div class="list_title clearfix">
        <h3 class="fl" id="新鲜水果">${category.name}</h3>
        <div class="subtitle fl">
        </div>
        <a href="product/list?categoryId=${category.id}" class="goods_more fr" id="fruit_more">查看更多 ></a>
    </div>

    <div class="goods_con clearfix">
        <div class="goods_banner fl"><img src="${ctx}/images/banner01.jpg"></div>
        <ul class="goods_list fl">
            <c:forEach items="${productsByCategory[category.id]}" var="product">
                <li>
                    <h4><a href="#">${product.name}</a></h4>
                    <a href="#"><img src="${product.imagePath}"></a>
                    <div class="prize">¥ ${product.price}</div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</c:forEach>

<%@include file="common/footer.jsp" %>
</body>
</html>