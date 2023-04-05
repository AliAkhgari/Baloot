<%@ page import="application.Baloot" %>
<%@ page import="entities.User" %>
<%@ page import="entities.Commodity" %><%--
  Created by IntelliJ IDEA.
  User: ali_akhgary
  Date: 4/5/23
  Time: 3:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
            padding: 5px
        }

        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<ul>
    <% User user = (User) request.getAttribute("user"); %>
    <li id="username">Username: <%=user.getUsername()%>
    </li>
    <li id="email">Email: <%=user.getEmail()%>
    </li>
    <li id="birthDate">Birth Date: <%=user.getBirthDate()%>
    </li>
    <li id="address"><%=user.getAddress()%>
    </li>
    <li id="credit">Credit: <%=user.getCredit()%>
    </li>
    <li>Current Buy List Price:<%=user.getCurrentBuyListPrice()%>
    </li>
    <li>
        <a href="/credit">Add Credit</a>
    </li>
    <li>
        <form action="" method="POST">
            <label>Submit & Pay</label>
            <input id="form_payment" type="hidden" name="userId" value="<%=user.getUsername()%>">
            <button type="submit">Payment</button>
        </form>
    </li>
</ul>
<table>
    <caption>
        <h2>Buy List</h2>
    </caption>
    <tbody>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th></th>
        <th></th>
    </tr>
    <% for (Commodity commodity : user.getBuyList()) { %>
    <tr>
        <td><%=commodity.getId()%>
        </td>
        <td><%=commodity.getName()%>
        </td>
        <td><%=Baloot.getInstance().getProviderNameOfCommodityId(commodity.getId())%>
        </td>
        <td><%=commodity.getPrice()%>
        </td>
        <td><%=String.join(", ", commodity.getCategories())%>
        </td>
        <td><%=commodity.getRating()%>
        </td>
        <td><%=commodity.getInStock()%>
        </td>
        <td><a href=<%="/commodities/" + commodity.getId()%>>Link</a></td>
        <td>
            <form action="" method="POST">
                <input id="form_commodity_id" type="hidden" name="commodityId" value=<%=commodity.getId()%>>
                <button type="submit">Remove</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>
