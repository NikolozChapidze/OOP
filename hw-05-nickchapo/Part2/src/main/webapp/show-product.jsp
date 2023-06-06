<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Chapo
  Date: 6/30/2022
  Time: 4:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.ProductCatalog,models.Product"%>
<%
    ProductCatalog productCatalog = (ProductCatalog) application.getAttribute(ProductCatalog.class.getSimpleName());
    Product product =  productCatalog.getProduct(request.getParameter("id"));
%>
<html>
<head>
    <title><%=product.getName()%></title>
</head>
<body>
<h1><%=product.getName()%></h1>
<img src=<%="store-images/"+product.getImageFile()%>>
<form action="Servlet" method="post">
    <p><%="$"+product.getPrice()%> <input name="productID" type="hidden" value="<%= product.getProductid() %>"/>
    <input type="submit" value="Add to cart"/>
    </p>
</form>
</body>
</html>
