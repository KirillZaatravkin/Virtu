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
<script> window.location = "header.jsp";</script>
<%}%>

<%@page import="source.*" %>

<% Settings sett = new Settings();
    Service servi = new Service();
    request.setCharacterEncoding("UTF-8");
    if (request.getParameter("update") == null) {
        int id = sett.setId(Integer.valueOf(request.getParameter("id")));
        sett = servi.getSetting(id);
    }


    if (request.getParameter("update") != null) {

        sett.setSettings(request.getParameter("settings"));
        System.out.println(sett.getSettings());

        sett.setId(Integer.valueOf(request.getParameter("id")));

        System.out.print(sett.getId());
        servi.updateSetting(sett);%>
       <script> window.location = "Service.jsp";</script>
<%

    }
%>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


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
    <form action="UpdateSetting.jsp" accept-charset="utf-8" method="POST">

        <p>Название настройки </p> <label><%=sett.getSetting_name()%></label>
        <p>Настройка </p>
        <input type="text" name="settings" value=<%=sett.getSettings()%>>
        <input type="hidden" value=<%=sett.getId()%> name="id">
        <input type="hidden" value="update" name="update">
        <button type="submit" name="save">save</button>
    </form>

</div>

<%@ include file="footer.jsp" %>
</body>