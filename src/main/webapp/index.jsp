<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@page import="source.*" %>


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
    <style> <%@include file="css/style.css"%> </style>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<div id="post">
    <p><a href="UserAdd.jsp">Добавить пользователя</a></p>
    <% UserSee userSee = new UserSee(); %>
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
        <% List<User> users = userSee.getAllUsers();
            for (int i = 0; i < users.size(); i++) {

        %>
        <tr>
            <td><%=users.get(i).getId()%></td>
            <td><%=users.get(i).getLogin()%></td>
            <td><%=users.get(i).getReg()%></td>
            <td> <a href="UserAdd.jsp?id=<%=users.get(i).getId()%>">Редактировать</a> </td>
        </tr>
        <%}%>
        </tbody>
    </table>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>