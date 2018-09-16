<%--
  Created by IntelliJ IDEA.
  User: Saphi
  Date: 19.08.2018
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>

<body>
<div>
    <h1>Login Page</h1>
</div>

<div>
    <div>
        <div>
            <h2>Users</h2>
        </div>
        <%
            /*
            List<String> lines = (List<String>) request.getAttribute("userNames");

            if (lines != null && !lines.isEmpty()) {
                out.println("<ui>");
                for (String s : lines) {
                    out.println("<li>" + s + "</li>");
                }
                out.println("</ui>");
            } else out.println("<p>There are no users yet!</p>");
            */
        %>
    </div>
</div>

<div>
    <button onclick="location.href='..'">Back to main</button>
</div>
</body>
</html>
