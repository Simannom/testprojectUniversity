<%@ page import="fillDB.SQLConnection" %>
<%@ page import="university_work.Student" %><%--
  Created by IntelliJ IDEA.
  User: Saphi
  Date: 19.08.2018
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DataBase</title>
</head>

<body>
<div>
    <h1>DataBase</h1>
</div>

<div>
    <div>
        <div>
            <h2>Students</h2>
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

        <form action="./list.jsp">
            <table border=1>
            <tr>
                <th> FullName </th>
                <th> GroupNumber </th>
                <th> Faculty </th>
                <th> ScholarshipType </th>
                <th> StartDate </th>

            </tr>

                <%
                    out.println(Student.stringListStudentsWeb());
                %>

                    <!--
                    <th><input type="button" id=b1 value="Submit" name="button" onClick='submitForm(this)'/></th>
                    <th><input type="button" id=b2 value="Update" name="button" onClick='submitForm(this)'/></th>
                    <th><input type="button" id=b3 value="Delete" name="button" onClick='submitForm(this)'/></th>
                </tr>
                -->

            </table>
            <input type='hidden' id='hid1'  name='hid1'>
        </form>
    </div>
</div>

<%
    /*
    попытка по кнопочке не только переходить, но и завершать соединение

    if(request.getParameter("Back to main") != null) {
        SQLConnection.breakConnection();

    }
    */
%>

<div>
    <button onclick="location.href='..'", >Back to main</button>
</div>
</body>
</html>
