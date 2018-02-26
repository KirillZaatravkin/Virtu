<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 10.11.2017
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>


<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header";</script>
<%}%>

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
    <form action="/settingsupdate" accept-charset="utf-8" method="POST">

        <p>Название настройки </p> <label><c:out value="${setting1.settingname}"/></label>
        <p>Настройка </p>
        <input type="text" name="settings" value="<c:out value="${setting1.settings}"/>"/>
        <input type="hidden" value="<c:out value="${setting1.id}"/>" name="id">
        <button type="submit" name="save">save</button>
    </form>

</div>

<%@ include file="footer.jsp" %>
</body>