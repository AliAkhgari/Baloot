<%--
  Created by IntelliJ IDEA.
  User: ali_akhgary
  Date: 4/5/23
  Time: 10:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<ul>
    <li id="email">username: <%= session.getAttribute("username") %>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/commodities">Commodities</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/buyList">Buy List</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/credit">Add Credit</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/logout">Log Out</a>
    </li>
</ul>

</body>
</html>
