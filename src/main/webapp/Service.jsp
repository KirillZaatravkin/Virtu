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
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<div id="post">
    <% Service service = new Service(); %>
    <label>Настройки</label>
    <table border=1>
        <thead>
        <tr><th>Номер</th>
            <th>Название</th>
            <th>Настройка</th>
        </tr>
        </thead>
        <tbody>
        <% List<Settings> settings=service.getAllSettings();
            for (int i = 0; i < settings.size(); i++) {
        %>
        <tr>
            <td><%=settings.get(i).getId()%></td>
            <td><%=settings.get(i).getSetting_name()%></td>
            <td><%=settings.get(i).getSettings()%></td>
            <td> <a href="UpdateSetting.jsp?id=<%=settings.get(i).getId()%>">Редактировать</a>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>




</div>
<%@ include file="footer.jsp" %>
</body>
</html>