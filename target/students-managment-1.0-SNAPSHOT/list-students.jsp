<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, students.managment.jdbc.*" %>
<%@ page import="students.managment.Student" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>List of all students</title>
    <link type="text/css" rel="stylesheet" href="./resources/css/style.css">
</head>
<body>
<main id="wrapper">
    <header id="header">
        <h2>FooBar University</h2>
    </header>
    <input class="add-student-button"
           type="button"
           value="Add student"
           onclick="window.location.href='add-student.jsp'"
    />
    <div id="container">
        <div id="content">
            <table>
                <tr>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="tempStudent" items="${requestScope.studentList}">
                    <c:url var="update" value="StudentControllerServlet">
                        <c:param name="command" value="LOAD"/>
                        <c:param name="studentId" value="${tempStudent.id}"/>
                    </c:url>

                    <c:url var="delete" value="StudentControllerServlet">
                        <c:param name="command" value="DELETE"/>
                        <c:param name="studentId" value="${tempStudent.id}"/>
                    </c:url>
                    <tr>
                        <td>${tempStudent.firstName}</td>
                        <td>${tempStudent.lastName}</td>
                        <td>${tempStudent.email}</td>
                        <td>
                            <a href="${update}">Update</a> | <a href="${delete}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false;">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</main>
</body>
</html>
