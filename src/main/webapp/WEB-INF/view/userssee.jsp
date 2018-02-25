<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%
if(session.getAttribute("login")==null) {
%>
<script> window.location = "header.jsp";</script>
<%
}
%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<div id="post">
    <p><a href="/useradd">Добавить пользователя</a></p>
    <label>Зарегистрированные пользователи</label>

    <table border=1>
        <thead>
        <tr>
            <th> ИД</th>
            <th>логин</th>
            <th>Дата регистрации</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${usersList}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.reg}"/></td>
        </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>