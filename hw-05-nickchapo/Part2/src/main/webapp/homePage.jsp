<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student Store</title>
</head>
<body>
<h1>Student Store</h1>
<p>Items available:</p>
<c:forEach items="${products}" var="element">
    <ul>
        <li><a href="show-product.jsp?id=${element.getProductid()}">${element.getName()}</a></li>
    </ul>
</c:forEach>
</body>
</html>
