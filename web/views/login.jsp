<%--
  Created by IntelliJ IDEA.
  User: Simannom
  Date: 19.08.2018
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
</head>

<body>


<div>
    <div>
        <div>
            <h2>Sign in</h2>
        </div>

        <form method="post">
            <label>Name:
                <input type="text" name="name"><br />
            </label>
            <label>Password:
                <input type="password" name="pass"><br />
            </label>

            <button type="submit" name="button" value="button1">Submit </button>
            <button type="submit" name="button" value="button2">Back to main</button>


        </form>
    </div>
</div>

</body>
</html>