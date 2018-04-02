<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 29.03.2018
  Time: 12:50
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
    <form action="/maskadd" accept-charset="utf-8" method="POST" name="AddMask">


        <p>
            <label id="lab">Прокуратура</label>
            <input type="text" name="title">
        </p>

        <p>
            <label id="lab">Маска для поиска</label>
            <input type="text" name="mask">
        </p>

        <button type="submit" name="save">Сохранить</button>
    </form>
</div>

<%@ include file="footer.jsp" %>
</body>