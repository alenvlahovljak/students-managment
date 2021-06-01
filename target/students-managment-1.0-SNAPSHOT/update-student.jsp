<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update student</title>
    <link type="text/css" rel="stylesheet" href="./resources/css/style.css">
    <link type="text/css" rel="stylesheet" href="./resources/css/add-student.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>FooBar University</h2>
    </div>
    <div id="container">
        <h3>Update student</h3>
        <form action="StudentControllerServlet" method="get">
            <input type="hidden" name="command" value="UPDATE"/>
            <input type="hidden" name="studentId" value="${requestScope.student.id}"/>
            <table>
                <tbody>
                <tr>
                    <td>
                        <label for="firstName">First name:</label>
                    </td>
                    <td>
                        <input id="firstName" name="firstName" value="${requestScope.student.firstName}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="lastName">Last name:</label>
                    </td>
                    <td>
                        <input id="lastName" name="lastName" value="${requestScope.student.lastName}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="email">Email:</label>
                    </td>
                    <td>
                        <input id="email" name="email" value="${requestScope.student.email}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input class="save" type="submit" value="Save"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <hr/>
            <p>
                <a href="StudentControllerServlet">
                    Back to list
                </a>
            </p>
        </form>
    </div>
</div>
</body>
</html>
