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
<%
CurrentDate cr=new CurrentDate();
cr.CurDate();
%>
<div id="post">
    <div style="width: 690px">
  <div id="analiz_right">
      <a href="Analiz.jsp?interval=year">
         <div id="analiz">Анализ текущего года</div>
      </a>
      <a href="Analiz.jsp?interval=month">
          <div id="analiz">Анализ текущего месяца</div>
      </a>
   </div>
     <div id="analiz_left">
          <div id="analiz">Анализ теукщей недели</div>
          <div id="analiz">Анализ тeкущего месяца</div>
     </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>