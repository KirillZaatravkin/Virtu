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
<%@ page import="source.system.model.ApGIBDD" %>
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
    <p><label id="lab_big">Загрузка баз ГИБДД</label></p>
    <%

        String myfile = request.getParameter("myfile");
        System.out.print(myfile);
        SettingsDao settingsDao = new SettingsDao();
        Settings sett = settingsDao.getSetting(1);
        String proverka = null;
        proverka = request.getParameter("proverka");
        String save = null;
        save = request.getParameter("save");
        ReadGibdd rao = new ReadGibdd();
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

    <form action="/gibdd" accept-charset="utf-8" method="POST" name="load">
        <input type="file" name="myfile">
        <div>
            <input type="submit" id="sub" name="save" value="Загрузить в БД">
            <input type="submit" id="sub" name="proverka" value="Проверить документ">
        </div>
    </form>


    <%
        if (myfile != null && proverka != null) {
            List<ApGIBDD> apGIBDD = rao.ReadExel(sett.getSettings() + myfile);
    %>

    <label id="lab_big">Проверка на правильность занесения данных ГИБДД</label>
    <p><label id="lab">Общее количсетво строк:<%=apGIBDD.size()%></label></p>
    <label id="lab_big">Пример:</label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Место жительства</th>
            <th>Водительское удоставирение</th>
            <th>Статья КоАП</th>
            <th>часть</th>
            <th>Дата совершения правонарушения</th>
            <th>Дата постановления</th>
            <th>Дата вступления в законную силу</th>
            <th>Вид наказания</th>
            <th>Номер протокола</th>
        </tr>
        </thead>
        <tbody>
        <%
            int p = (apGIBDD.size() / 100) + 1;
            for (int i = 0; i < p; i++) {

        %>
        <tr>
            <td><%=apGIBDD.get(i).getLastName()%>
            </td>
            <td><%= apGIBDD.get(i).getFirstName()%>
            </td>
            <td><%= apGIBDD.get(i).getMiddleName()%>
            </td>
            <td><%= apGIBDD.get(i).getBirthDay()%>
            </td>
            <td><%= apGIBDD.get(i).getFacktAddr()%>
            </td>
            <td><%= apGIBDD.get(i).getVodUd()%>
            </td>
            <td><%= apGIBDD.get(i).getArticle()%>
            </td>
            <td><%= apGIBDD.get(i).getCact()%>
            </td>
            <td><%= apGIBDD.get(i).getDateP()%>
            </td>
            <td><%= apGIBDD.get(i).getDatePost()%>
            </td>
            <td><%= apGIBDD.get(i).getDateZak()%>
            </td>
            <td><%= apGIBDD.get(i).getNakaz()%>
            </td>
            <td><%= apGIBDD.get(i).getProtokolN()%>
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
