<%--
  Created by IntelliJ IDEA.
  User: Chapo
  Date: 6/30/2022
  Time: 12:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome <%= request.getParameter("userName")%></title>
</head>
<body>
<a>Welcome <%= request.getParameter("userName")%></a>
</body>
</html>
