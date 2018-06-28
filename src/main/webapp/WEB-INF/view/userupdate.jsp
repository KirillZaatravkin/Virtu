<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 10.11.2017
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header";</script>
<%}%>


<%request.setCharacterEncoding("UTF-8");%>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


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
    <form action="/userupdate" accept-charset="utf-8" method="POST" name="AddUser">


        <input type="hidden" name="id" value="<c:out value="${id}"/>" >
        <p>
            <label id="lab">ФИО</label>
             <input type="text" name="fio" value="<c:out value="${fio}"/>" >
        </p>

        <p>
            <label id="lab">логин</label>
            <input type="text" name="login" value="<c:out value="${login}"/>" >
        </p>

        <p>
            <label id="lab">пароль</label>
            <input type="text" name="password" value="<c:out value="${password}"/>" >
        </p>

        <p>
            <label id="lab">группа </label>
            <input type="text" name="groups" value="<c:out value="${groups}"/>" >
        </p>
        <button type="submit" name="save">Сохранить</button>
    </form>
</div>

<%@ include file="footer.jsp" %>
</body>