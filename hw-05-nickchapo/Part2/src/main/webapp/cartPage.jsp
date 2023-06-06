<%--
  Created by IntelliJ IDEA.
  User: Chapo
  Date: 6/30/2022
  Time: 12:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ page import="models.Product" %>
<%@ page import="models.ShoppingCart" %>
<%
    double sumPrice = (double) request.getAttribute("sumPrice");
    ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute(ShoppingCart.class.getSimpleName());
%>

<html>
<head>
    <title>Shopping Cart</title>
</head>
<body>
<h1>Shopping Cart</h1>
<form action="Servlet" method="post">
    <c:forEach items="${userProducts}" var="element">
        <ul>
            <li>
                <input name="quantityOf${element.getProductid()}" type="text"
                       value=<%=shoppingCart.getProductQuantity(((Product)pageContext.getAttribute("element")).getProductid())%>>
                    ${element.getName()}, ${element.getPrice()}</li>
        </ul>
    </c:forEach>
    <div style="text-align: center">

        <p>
            <%="Total: $" + sumPrice + " "%><input type="submit" name="Update Cart" value="Update Cart"/>
        </p>
    </div>
</form>

<div style="text-align: center">
    <a href="homePage.jsp">Continue Shopping</a>
</div>

</body>
</html>
