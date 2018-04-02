<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 15.02.2018
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@page import="source.*" %>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%
    if(session.getAttribute("login")==null) {
%>
<script> window.location = "header";</script>
<%

    }
%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
    <script>

        function  preload_year () {
            window.location = "/analiz?interval=year";
        }
        function  preload_month() {
            window.location = "/analiz?interval=month";
        }
        function preload_lastload() {
            window.location="/analiz?interval=lastload";
        }
    </script>
</head>

<div id="preloader_malc">
    <div>
        <img src="/media/load.gif">
        Подождите, идет загрузка  ...

    </div>
</div>
<%
if (request.getParameter("interval").equals("year"))
{
    %>
<script>preload_year(); </script>
<%} else if((request.getParameter("interval").equals("month"))
)
{%>
<script>preload_month();</script>
<%} else {%>
  <script>preload_lastload();</script>
<%}%>
</html>

