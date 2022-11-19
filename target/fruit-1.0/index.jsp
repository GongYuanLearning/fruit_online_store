<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.lzj.fruit.dto.Product" %>
<%@ page import="java.math.BigDecimal" %><%--
  author: Zhijie Liu
  version: 1.0
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="common/ctx.jsp" %>
<%!
    private static void addProduct(String[] names, BigDecimal[] prices, String[] imagePaths, List<Product> products) {
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            BigDecimal price = prices[i];
            String imagePath = imagePaths[i];

            Product product = new Product();
            product.setName(name);
            product.setImagePath(imagePath);
            product.setPrice(price);
            products.add(product);
        }
    }
%>
<%
    List<String> categories = new ArrayList<>();
    List<String> categoriesClasses = new ArrayList<>();
    categories.add("新鲜水果");
    categoriesClasses.add("fruit");
    categories.add("海鲜水产");
    categoriesClasses.add("seafood");
    categories.add("猪牛羊肉");
    categoriesClasses.add("meet");
    categories.add("禽类蛋品");
    categoriesClasses.add("egg");
    categories.add("新鲜蔬菜");
    categoriesClasses.add("vegetables");
    categories.add("速冻食品");
    categoriesClasses.add("ice");

    Map<String, List<Product>> productsByCategory = new HashMap<>();
    for(String category : categories) {
        List<Product> products = new ArrayList<>();
        String[] names = new String[] {"草莓", "葡萄", "柠檬", "奇异果"};
        BigDecimal[] prices = new BigDecimal[] {BigDecimal.valueOf(30), BigDecimal.valueOf(10), BigDecimal.valueOf(15), BigDecimal.valueOf(20)};
        String[] imagePaths = new String[] {"images/goods/goods003.jpg", "images/goods/goods002.jpg", "images/goods/goods001.jpg", "images/goods/goods012.jpg"};
        addProduct(names, prices, imagePaths, products);
        productsByCategory.put(category, products);
    }

    request.setAttribute("categories", categories);
    request.setAttribute("categoriesClasses", categoriesClasses);
    request.setAttribute("productsByCategory", productsByCategory);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>天天生鲜-首页</title>
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <script type="text/javascript" src="js/jquery-3.6.1.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript" src="js/slide.js"></script>
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
            <li><a href="#${category}" class="${categoriesClasses[status.index]}">${category}</a></li>
        </c:forEach>
    </ul>
    <div class="slide fl">
        <ul class="slide_pics">
            <li><img src="images/slide.jpg" alt="幻灯片"></li>
            <li><img src="images/slide02.jpg" alt="幻灯片"></li>
            <li><img src="images/slide03.jpg" alt="幻灯片"></li>
            <li><img src="images/slide04.jpg" alt="幻灯片"></li>
        </ul>
        <div class="prev"></div>
        <div class="next"></div>
        <ul class="points"></ul>
    </div>
    <div class="adv fl">
        <a href="#"><img src="images/adv01.jpg"></a>
        <a href="#"><img src="images/adv02.jpg"></a>
    </div>
</div>

<c:forEach items="${productsByCategory}" var="entry">
<div class="list_model">
    <div class="list_title clearfix">
        <h3 class="fl" id="新鲜水果">${entry.key}</h3>
        <div class="subtitle fl">
        </div>
        <a href="#" class="goods_more fr" id="fruit_more">查看更多 ></a>
    </div>

    <div class="goods_con clearfix">
        <div class="goods_banner fl"><img src="images/banner01.jpg"></div>
        <ul class="goods_list fl">
            <c:forEach items="${entry.value}" var="product">
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