<%--
  Created by IntelliJ IDEA.
  User: ali_akhgary
  Date: 4/3/23
  Time: 4:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <style>
        h1 {
            color: rgb(207, 3, 3);
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<h1>
    Error:
</h1>
<br>
<h3>
    <%= request.getAttribute("errorMessage") %>
</h3>
</body>
</html>
