<%@ page import="entities.Commodity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entities.Comment" %>
<%@ page import="application.Baloot" %><%--
  Created by IntelliJ IDEA.
  User: ali_akhgary
  Date: 4/4/23
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Commodity</title>
    <style>
        li {
            padding: 5px;
        }

        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<span>username: <%= session.getAttribute("username") %></span>
<br>
<% Commodity commodity = (Commodity) request.getAttribute("commodity"); %>
<ul>
    <li id="id">Id: <%=commodity.getId()%>
    </li>
    <li id="name">Name: <%=commodity.getName()%>
    </li>

    <li id="providerName">Provider Name: <%=request.getAttribute("provider_name")%>
    </li>
    <li id="price">Price: <%=commodity.getPrice()%>
    </li>
    <li id="categories">Categories: <%=String.join(", ", commodity.getCategories())%>
    </li>
    <li id="rating">Rating: <%=commodity.getRating()%>
    </li>
    <li id="inStock">In Stock: <%=commodity.getInStock()%>
    </li>
</ul>

<label>Add Your Comment:</label>
<form action="" method="post">
    <input type="text" name="comment" value=""/>
    <button type="submit">submit</button>
</form>
<br>
<form action="" method="POST">
    <label>Rate(between 1 and 10):</label>
    <input type="number" id="quantity" name="quantity" min="1" max="10">
    <button type="submit">Rate</button>
</form>
<br>
<form action="" method="POST">
    <input type="hidden" name="add_to_buy_list"/>
    <button type="submit">Add to BuyList</button>
</form>
<br/>
<table>
    <caption><h2>Comments</h2></caption>
    <tr>
        <th>username</th>
        <th>comment</th>
        <th>date</th>
        <th>likes</th>
        <th>dislikes</th>
    </tr>
    <% ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments"); %>
    <% for (Comment comment : comments) { %>
    <tr>
        <td><%=comment.getUserEmail()%>
        </td>
        <td><%=comment.getText()%>
        </td>
        <td><%=comment.getDate()%>
        </td>
        <td>
            <form action="" method="POST">
                <label for=""><%=comment.getLike()%>
                </label>
                <input type="hidden" name="comment_id" value="<%=comment.getId()%>">
                <input
                        type="hidden"
                        name="like"
                        value="1"
                />
                <button type="submit">like</button>
            </form>
        </td>
        <td>
            <form action="" method="POST">
                <label for=""><%=comment.getDislike()%>
                </label>
                <input type="hidden" name="comment_id" value="<%=comment.getId()%>">
                <input
                        type="hidden"
                        name="dislike"
                        value="-1"
                />
                <button type="submit">dislike</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<br><br>
<table>
    <caption><h2>Suggested Commodities</h2></caption>
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
    <% for (Commodity suggestedCommodity : (ArrayList<Commodity>) request.getAttribute("suggestedCommodities")) { %>
    <tr>
        <td><%=suggestedCommodity.getId()%>
        </td>
        <td><%=suggestedCommodity.getName()%>
        </td>
        <td><%=Baloot.getInstance().getProviderNameOfCommodityId(suggestedCommodity.getId())%>
        </td>
        <td><%=suggestedCommodity.getPrice()%>
        </td>
        <td><%=String.join(", ", suggestedCommodity.getCategories())%>
        </td>
        <td><%=suggestedCommodity.getRating()%>
        </td>
        <td><%=suggestedCommodity.getInStock()%>
        </td>
        <td><a href="${pageContext.request.contextPath}/commodities/<%=suggestedCommodity.getId()%>}">Link</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>

