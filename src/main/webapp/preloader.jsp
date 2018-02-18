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
<script> window.location = "header.jsp";</script>
<%

    }
%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <title>ИТО</title>
    <style> <%@include file="css/style.css"%> </style>
    <script>

        function  preload () {
            window.location = "/Analiz.jsp?interval=year";
        }
    </script>
</head>





<div id="preloader_malc">
    <div>
        Подождите, идет загрузка  ...

        <img src="css/media/load.gif">
    </div>
</div>


<script>
    preload();
</script>

</html>

