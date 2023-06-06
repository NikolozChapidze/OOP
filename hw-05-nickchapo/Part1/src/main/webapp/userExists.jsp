<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
<h1>The Name <%= request.getParameter("userName")%> is Already In Use</h1>
<p>Please enter another name and password.</p>
<form action="CreateAccountServlet" method="post">
    <p>User Name: <input type="text" name="userName"></p>
    <p>Password: <input type="password" name="userPassword"> <input type="submit" name="Login" value="Login"></p>
</form>
</body>
</html>
