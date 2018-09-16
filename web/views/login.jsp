<%--
  Created by IntelliJ IDEA.
  User: Saphi
  Date: 19.08.2018
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>

<body>

    <c:if test="${violations != null}">
        <c:forEach items="${violations}" var="violation">
            <p>${violation}.</p>
        </c:forEach>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="UserName"> Пользователь : </label>
        <input type="text" name="UserName" id="UserName" value="${UserName}">
        <label for="password"> Пароль: </label>
        <input type="text" name="password" id="password" value="${password}">

            <input type="submit" name="login" value="Login">
    </form>


</body>
</html>