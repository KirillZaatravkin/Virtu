<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 20.11.2017
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@page import="source.*" %>
<%@ page import="java.util.List" %>
<%@ page import="javafx.scene.input.DataFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="source.system.model.Settings" %>
<%@ page import="source.system.dao.SettingsDao" %>
<%@ page import="source.system.model.ApGIBDDStat" %>
<%@ page import="source.system.model.ApOVDStat" %>

<%request.setCharacterEncoding("UTF-8");
String interval =request.getParameter("interval");%>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
    <script type="text/javascript">
        window.onload = function() {
            setTimeout(function(){
                document.getElementById('preloader_malc').style.display='none';
            }, 400);
        };
    </script>
</head>

<div id="preloader_malc">
    <div>
        Подождите, идет загрузка  ...
    </div>
</div>


<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<body>
<div id="main_block">Анализ</div>
<div id="post" >

<label></label>

    <%
        SettingsDao settingsDao = new SettingsDao();
        Settings sett = settingsDao.getSetting(2);%>
    <div style="text-align: right; ";><label id="lab"style="background-color: yellow" >Папка, в которую помещен отчет: <%=sett.getSettings()%></label></div>
<%



        String article =  null;
        StatResedivOVD sr = new StatResedivOVD();
        StatResedivGIBDD sg= new StatResedivGIBDD();

        article=request.getParameter("article");
        if (article=="")
        {
            article=null;
        }
            if(request.getParameter("flag")!="")
            {
                List <ApOVDStat> apOVDStats =sr.FilterStat(null,null,null,"kol",interval,"%","%");
                List <ApGIBDDStat>  apGIBDDStats= sg.FilterStat(null,null,null,"kol",interval,"%","%");
                String d = Thread.currentThread().getName();
                System.out.print(d);
                int kolNarush=0;
                int kolFace=0;
                int kolRes =0;
                kolNarush= kolNarush+sr.KolNarush(article, "%",null,null,true)+sg.KolNarush(article, "%",null,null,true);
                kolFace=kolFace+sr.KolFace(article,"%",null,null,true)+sg.KolFace(article,"%",null,null,true);
                kolRes = kolRes+apOVDStats.size()+apGIBDDStats.size();

                    System.out.println("dfd");
                    CreateWord cw= new CreateWord();
                    cw.Resediv(apOVDStats,apGIBDDStats,kolNarush,kolFace,kolRes);

                %>

    <label id="lab_big">Общая сводка</label>

    <table border=1>
        <thead>
        <tr>

            <th>Общее кол-во правонарушений на текущий год</th>
            <th>Общее кол-во лиц, привлеченных к ответственности за текущий год</th>
            <% if (interval.equals("year")) {%>
            <th>Выявлено правонарушителей-рецидивистов за год </th>
            <%} if (interval.equals("month")) {%>
            <th>Выявлено правонарушителей-рецидивистов за месяц </th>
            <%}%>

        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%=kolNarush%></td>
            <td><%= kolFace%></td>
            <td><%= kolRes%></td>
        </tr>
        </tbody>
    </table>

<label id="lab_big">Правонарушители-рецидивисты</label>
       <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Статья</th>
            <th>Часть</th>
            <th>Кол-во незакрытых правонарушений</th>
            <th>Дата последнего постановления</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < apOVDStats.size(); i++) {

        %>
        <tr>
            <td><%=apOVDStats.get(i).getLastName()%></td>
            <td><%= apOVDStats.get(i).getFirstName()%></td>
            <td><%= apOVDStats.get(i).getMiddleName()%></td>
            <td><%= apOVDStats.get(i).getBirthDay()%></td>
            <td><%= apOVDStats.get(i).getArticle()%></td>
            <td><%= apOVDStats.get(i).getCact()%></td>
            <td><%= apOVDStats.get(i).getKol()%></td>
            <td><%= apOVDStats.get(i).getDateP()%></td>
            <td> <form method="post" action="/echofilter">
                <input type="hidden" value=<%=apOVDStats.get(i).getLastName()%> name="lastname">
                <input type="hidden" value=<%=apOVDStats.get(i).getFirstName()%> name="firstname">
                <input type="hidden" value=<%=apOVDStats.get(i).getMiddleName()%> name="middlename">
                <input type="hidden" value=<%=apOVDStats.get(i).getBirthDay()%> name="birthday">
                <button type="submit">Подробно о гржданине</button>
            </form>
            </td>

        </tr>
        <%

            }
        %>
        </tbody>
    </table>
</div>
<%}%>
<%@ include file="footer.jsp" %>
</body>

