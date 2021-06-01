<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add student</title>
    <link type="text/css" rel="stylesheet" href="./resources/css/style.css">
    <link type="text/css" rel="stylesheet" href="./resources/css/add-student.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <h2>FooBar University</h2>
    </div>
    <div id="container">
        <h3>Add student</h3>
        <form action="StudentControllerServlet" method="post">
            <input type="hidden" name="command" value="ADD"/>
            <table>
                <tbody>
                <tr>
                    <td>
                        <label for="firstName">First name:</label>
                    </td>
                    <td>
                        <input id="firstName" name="firstName"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="lastName">Last name:</label>
                    </td>
                    <td>
                        <input id="lastName" name="lastName"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="email">Email:</label>
                    </td>
                    <td>
                        <input id="email" name="email"/>
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
