<%@ page import="entities.Commodity" %>
<%@ page import="application.Baloot" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: ali_akhgary
  Date: 4/4/23
  Time: 12:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<p id="username">username: <%= session.getAttribute("username") %>
</p>
<br><br>
<form action="commodities" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_category">Search By Cagtegory</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="commodities" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Rate</button>
    <button type="submit" name="action" value="sort_by_price">Price</button>
</form>
<br><br>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>
    <%
        ArrayList<Commodity> commodities = (ArrayList<Commodity>) request.getAttribute("commodities");
        for (Commodity commodity : commodities) {
    %>
    <tr>
        <td>
            <%= commodity.getId()%>
        </td>
        <td>
            <%= commodity.getName()%>
        </td>
        <td>
            <%= commodity.getProviderId()%>
        </td>
        <td>
            <%= commodity.getPrice()%>
        </td>
        <td>
            <%= String.join(", ", commodity.getCategories())%>
        </td>
        <td>
            <%= commodity.getRating()%>
        </td>
        <td>
            <%= commodity.getInStock()%>
        </td>
        <td><a href=<%= request.getContextPath() + "/commodities/" + commodity.getId()%>>Link</a></td>
    </tr>


    <%
        }
    %>

</table>
</body>
</html>
