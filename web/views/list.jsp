<%@ page import="university_work.StudentResult" %>
<%@ page import="cache.StudentCacheManager" %><%--
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


        <form action="${pageContext.request.contextPath}/list" method="post">
            <table border=1>
            <tr>
                <th> FullName </th>
                <th> GroupNumber </th>
                <th> Faculty </th>
                <th> ScholarshipType </th>
                <th> StartDate </th>

            </tr>

                <%
                    //без кэша
                    //out.println(StudentResult.stringListStudentsWeb());
                    //с кэшем
                    out.println(StudentResult.stringListStudentsTable(StudentCacheManager.select()));
                %>



                <!--
                <button type="submit" name="button" value="list_b3">Add</button>
                -->
            </table>
            <input type='hidden' id='hid1'  name='hid1'>

            <button type="submit" name="button" value="list_button">Back to main</button>
        </form>
    </div>
</div>



</body>
</html>
