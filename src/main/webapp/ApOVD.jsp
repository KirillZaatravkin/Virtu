<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header.jsp";</script>
<%}%>
<%@ page import="source.ReadApOVD" %>
<%@ page import="source.ApOVD" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="source.Service" %>
<%@ page import="java.util.Set" %>
<%@ page import="source.Settings" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="css/style.css"%> </style>
</head>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<body>
<div id="post">
    <%
        String myfile = null;
        myfile = request.getParameter("myfile");
        Service service = new Service();
        Settings sett = service.getSetting(1);%>
    <label>Папка, в которую следует поместить файл:<%=sett.getSettings()%>
    </label>
    <% if (myfile != null) {
        System.out.print(myfile);
        ReadApOVD rao = new ReadApOVD();
        List<ApOVD> apOVDS = new ArrayList<ApOVD>();

        int i = rao.WriteToBD(rao.ReadExel(sett.getSettings() + myfile));

    %>
    <label>Обработано записей:<%=i%>
    </label>
    <%
        }
    %>
    <form action="ApOVD.jsp" accept-charset="utf-8" method="POST" name="load">
        <input type="file" name="myfile">
        <button type="submit" name="save">ок</button>
    </form>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
