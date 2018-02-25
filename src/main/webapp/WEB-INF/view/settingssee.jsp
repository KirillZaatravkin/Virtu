<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@page import="source.*" %>
<%@ page import="source.system.model.Settings" %>
<%@ page import="source.system.dao.SettingsDao" %>


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
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<div id="post">
    <label>Настройки</label>
    <table border=1>
        <thead>
        <tr>
            <th>Название</th>
            <th>Настройка</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${settingsList}" var="setting">
        <tr>
            <td><c:out value="${setting.settingname}"/></td>
            <td><c:out value="${setting.settings}"/></td>
            <td> <a href="/settingsupdate?id=${setting.id}">Редактировать</a>

        </tr>
        </c:forEach>
        </tbody>
    </table>


    </td>


</div>
<%@ include file="footer.jsp" %>
</body>
</html>