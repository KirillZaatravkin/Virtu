<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "/header";</script>
<%}%>
<%@ page import="source.ReadApOVD" %>
<%@ page import="source.system.model.ApOVD" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="source.system.dao.SettingsDao" %>
<%@ page import="java.util.Set" %>
<%@ page import="source.system.model.Settings" %>
<%@ page import="source.ReadGibdd" %>
<%@ page import="source.ReadPrist" %>
<%@ page import="source.system.model.ApPrist" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<body>
<div id="main_block">Загрузка данных</div>
<div id="post">
    <%@ include file="vertmenu2.jsp" %>
    <p><label id="lab_big">Загрузка баз ОВД</label></p>
    <%

        String myfile = request.getParameter("myfile");
        System.out.print(myfile);
        SettingsDao settingsDao = new SettingsDao();
        Settings sett = settingsDao.getSetting(1);
        String proverka = null;
        proverka = request.getParameter("proverka");
        String save = null;
        save = request.getParameter("save");
        ReadApOVD rao = new ReadApOVD();
    %>
    <label>Папка, в которую следует поместить файл:<%=sett.getSettings()%>
    </label>
    <% if (myfile != null && save != null) {

        int i = rao.WriteToBD(rao.ReadExel(sett.getSettings() + myfile));

    %>
    <label>Обработано записей:<%=i%>
    </label>
    <%
        }
    %>

    <form action="/apovd" accept-charset="utf-8" method="POST" name="load">
        <input type="file" name="myfile">
        <div>
            <input type="submit" id="sub" name="save" value="Загрузить в БД">
            <input type="submit" id="sub" name="proverka" value="Проверить документ">
        </div>
    </form>


    <%
        if (myfile != null && proverka != null) {
            List<ApOVD> apOVD = rao.ReadExel(sett.getSettings() + myfile);
    %>

    <label id="lab_big">Проверка на правильность занесения данных ОВД</label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Место жительства</th>
            <th>Место прописки</th>
            <th>Серия паспорта</th>
            <th>Номер паспорта</th>
            <th>Статья КоАП</th>
            <th>часть</th>
            <th>Дата совершения правонарушения</th>
            <th>Дата вступления в законную силу</th>
        </tr>
        </thead>
        <tbody>
        <%
            int p = (apOVD.size() / 100) + 1;
            for (int i = 0; i < p; i++) {

        %>
        <tr>
            <td><%=apOVD.get(i).getFirstName()%>
            </td>
            <td><%= apOVD.get(i).getLastName()%>
            </td>
            <td><%= apOVD.get(i).getMiddleName()%>
            </td>
            <td><%= apOVD.get(i).getBirthDay()%>
            </td>
            <td><%= apOVD.get(i).getFacktAddr()%>
            </td>
            <td><%= apOVD.get(i).getResAddr()%>
            </td>
            <td><%= apOVD.get(i).getPasportS()%>
            </td>
            <td><%= apOVD.get(i).getPasportN()%>
            </td>
            <td><%= apOVD.get(i).getArticle()%>
            </td>
            <td><%= apOVD.get(i).getCact()%>
            </td>
            <td><%= apOVD.get(i).getDateP()%>
            </td>
            <td><%= apOVD.get(i).getDateZak()%>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <% }%>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
