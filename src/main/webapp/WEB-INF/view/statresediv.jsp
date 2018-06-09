<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 20.11.2017
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@page import="source.*" %>
<%@ page import="source.system.dao.MaskDAO" %>
<%@ page import="source.system.model.ApGIBDDStat" %>
<%@ page import="source.system.model.ApPristStat" %>
<%@ page import="source.system.model.Mask" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="source.system.model.ApOVDStat" %>

<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header";</script>
<%}%>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>

    <script>

        function mydate() {

            document.getElementById("dt").hidden = false;
            document.getElementById("ndt").hidden = true;
        }

        function mydate2() {

            document.getElementById("dt2").hidden = false;
            document.getElementById("ndt2").hidden = true;
        }
        function mydate1() {
            d = new Date(document.getElementById("dt").value);
            dt = d.getDate();
            mn = d.getMonth();
            mn++;
            yy = d.getFullYear();
            document.getElementById("ndt").value = dt + "." + mn + "." + yy;
            if (document.getElementById("ndt").value == "") {
                document.getElementById("ndt").value = "00.00.0000";
            }
            document.getElementById("ndt").hidden = false;
            document.getElementById("dt").hidden = true;
        }

        function mydate12() {
            d = new Date(document.getElementById("dt2").value);
            dt = d.getDate();
            mn = d.getMonth();
            mn++;
            yy = d.getFullYear();
            document.getElementById("ndt2").value = dt + "." + mn + "." + yy;
            if (document.getElementById("ndt2").value == "") {
                document.getElementById("ndt2").value = "00.00.0000";
            }
            document.getElementById("ndt2").hidden = false;
            document.getElementById("dt2").hidden = true;
        }
    </script>
</head>
    <%
        System.out.print(request.getParameter("d2"));
        String article ="";
        String cact="";
        String ovd=request.getParameter("ovd");
        String gibdd=request.getParameter("gibdd");
        String prist=request.getParameter("prist");
        StatResedivOVD sr = new StatResedivOVD();
        StatResedivGIBDD sg=new StatResedivGIBDD();
        StatResedivPrist sp=new StatResedivPrist();
        String sort=request.getParameter("sort");
        article=request.getParameter("article");
        cact=request.getParameter("cact");
        String regionMask= request.getParameter("regionMask");

        if (article=="")
        {
            article=null;
        }

        if (cact==""|| cact==null)
        {
            cact="%";
        }%>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
    <%
    String S1 = request.getParameter("d1");
    String S2 = request.getParameter("d2");

    MaskDAO maskDAO=new MaskDAO();
    List<Mask> maskList= maskDAO.getAllMask();

%>

<body>
<div id="main_block">Поиск рецедивистов</div>
<div id="post">

    <label></label>


    <form action="statresediv" method="POST" name="filter">
        <div id="kol">

            <div id="lab_big">
                <label>Данные для поиска</label>
            </div>

            <div id="left_kol">

                <div>
                    <div><label label id="lab"> Статья </label></div>
                    <% if (article == null) { %>
                    <input type="text" name="article">
                    <% } else {
                    %>
                    <input type="text" name="article" value="<%=article%>">
                    <%}%>
                </div>
                <div>
                    <div><label label id="lab">Часть </label></div>
                    <% if (cact.equals("%")) { %>
                    <input type="text" name="cact">
                    <% } else {
                    %>
                    <input type="text" name=cact value="<%=cact%>">
                    <%}%>
                </div>

                <div>
                    <div><label id="lab">Район/город</label></div>
                    <select name="regionMask"><% for (int i = 0; i < maskList.size(); i++) { %>
                        <option value="<%=maskList.get(i).getMask()%>"><%=maskList.get(i).getTitle()%>
                        </option>
                        <%} %>

                    </select>
                </div>
                <div>
                    <p><label id="lab">Выбор баз данных</label></p>
                    <p><input type="checkbox" <%if (ovd!=null) {%> checked<%}%> name="ovd" value="ovd">ОВД</p>
                    <p><input type="checkbox" name="gibdd"<%if (gibdd!=null) {%> checked<%}%> value="gibdd">ГИБДД</p>
                    <p><input type="checkbox" <%if (prist!=null) {%> checked<%}%> name="prist" value="prist">ФССП</p>

                </div>
            </div>

            <div id="right_kol">
                <div><label id="lab">Период совершения АП</label></div>
                <input type="date" id="dt" onchange="mydate1();"/>
                <input type="text" name="d1" id="ndt" onclick="mydate();" hidden/>

                <input type="date" id="dt2" onchange="mydate12();"/>
                <input type="text" name="d2" id="ndt2" onclick="mydate2();" hidden/>

                <p><label id="lab">Выбор сортировки:</label></p>
                <p><input name="sort" type="radio" value="datep" checked>Дата совершения последнего АП</p>
                <p><input name="sort" type="radio" value="lastname">Фамилия</p>
                <p><input name="sort" type="radio" value="kol"> Кол-во преступлений </p>
                <p><input name="sort" type="radio" value="article"> Статья АП </p>


            </div>
        </div>
        <label>Сгенерировать отчет</label><input type="checkbox" name="doc" checked value="doc">
        <button id="but_ok" type="submit" name="save" onclick="mydate();">Искать</button>
        <button id="but_ok" type="reset">Очистить поля</button>


    </form>

    <%
        try {

            if(request.getParameter("ovd") == null && request.getParameter("gibdd") == null && request.getParameter("prist") == null && request.getParameter("save")!=null)
            {%> <script> alert("Выберите хотя бы одну базу для анализа");</script> <%}


            if (request.getParameter("d1") != null && request.getParameter("d2") != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                String STRd1 = request.getParameter("d1");
                Date d1 = format.parse(STRd1);
                java.sql.Date SQLd1 = new java.sql.Date(d1.getTime());

                String STRd2 = request.getParameter("d2");
                Date d2 = format.parse(STRd2);
                java.sql.Date SQLd2 = new java.sql.Date(d2.getTime());

                List<ApOVDStat> apOVDStats = new ArrayList<ApOVDStat>();
                List<ApGIBDDStat> apGIBDDStat = new ArrayList<ApGIBDDStat>();
                List<ApPristStat> apPristStat = new ArrayList<ApPristStat>();
                int kolNarush = 0;
                int kolFace = 0;
                int kolRes = 0;
                if (request.getParameter("ovd") != null) {
                    apOVDStats = sr.FilterStat(article, SQLd1, SQLd2, sort, "found", cact, regionMask);
                    kolNarush = kolNarush + sr.KolNarush(article, regionMask, SQLd1, SQLd2, false);
                    kolFace = kolFace + sr.KolFace(article, regionMask, SQLd1, SQLd2, false);
                    kolRes = kolRes + apOVDStats.size();

                }
                if (request.getParameter("gibdd") != null) {
                    apGIBDDStat = sg.FilterStat(article, SQLd1, SQLd2, sort, "found", cact, regionMask);
                    kolNarush = kolNarush + sg.KolNarush(article, regionMask, SQLd1, SQLd2, false);
                    kolFace = kolFace + sg.KolFace(article, regionMask, SQLd1, SQLd2, false);
                    kolRes = kolRes + apGIBDDStat.size();
                }
                if (request.getParameter("prist") != null) {
                    apPristStat = sp.FilterStat(article, SQLd1, SQLd2, sort, "found", cact, regionMask);
                    kolNarush = kolNarush + sp.KolNarush(article, regionMask, SQLd1, SQLd2, false);
                    kolFace = kolFace + sp.KolFace(article, regionMask, SQLd1, SQLd2, false);
                    kolRes = kolRes + apPristStat.size();
                }

                if (request.getParameter("doc") != null) {

                    CreateWord cw = new CreateWord();
                   cw.Resediv(apOVDStats, apGIBDDStat, apPristStat, kolNarush, kolFace, kolRes);
                }
    %>

    <label id="lab_big">Выборка за <%=SQLd1%> - <%=SQLd2%> по <%=maskDAO.getTitle(regionMask)%>
    </label>

    <p><label id="lab_big">Общая сводка</label></p>

    <table border=1>
        <thead>
        <tr>

            <th>Общее кол-во правонарушений на заданный периодна данной территории</th>
            <th>Общее кол-во лиц, привлеченных к ответственности на заданный период на данной территории</th>
            <th>Выявлено правонарушителей-рецидивистов по заданному поиску</th>

        </tr>
        </thead>
        <tbody>
        <tr>
            <td><%=kolNarush%>
            </td>
            <td><%= kolFace%>
            </td>
            <td><%= kolRes%>
            </td>
        </tr>
        </tbody>
    </table>
    <% if (apOVDStats.size() > 0) {%>
    <label id="lab_big">Правонарушители-рецидивисты (данные согласно базам ОВД)</label>
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
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < apOVDStats.size(); i++) {


        %>
        <tr>
            <td><%=apOVDStats.get(i).getLastName()%>
            </td>
            <td><%= apOVDStats.get(i).getFirstName()%>
            </td>
            <td><%= apOVDStats.get(i).getMiddleName()%>
            </td>
            <td><%= apOVDStats.get(i).getBirthDay()%>
            </td>
            <td><%= apOVDStats.get(i).getArticle()%>
            </td>
            <td><%= apOVDStats.get(i).getCact()%>
            </td>
            <td><%= apOVDStats.get(i).getKol()%>
            </td>
            <td>
                <form method="post" action="/echofilter">
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
    <%
        }

        if (apGIBDDStat.size() > 0) {
    %>

    <label id="lab_big">Правонарушители-рецидивисты (данные согласно базам ГИБДД)</label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Место жительства</th>
            <th>Статья</th>
            <th>Часть</th>
            <th>Кол-во незакрытых правонарушений</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < apGIBDDStat.size(); i++) {

        %>
        <tr>
            <td><%=apGIBDDStat.get(i).getLastName()%>
            </td>
            <td><%= apGIBDDStat.get(i).getFirstName()%>
            </td>
            <td><%= apGIBDDStat.get(i).getMiddleName()%>
            </td>
            <td><%= apGIBDDStat.get(i).getBirthDay()%>
            </td>
            <td><%= apGIBDDStat.get(i).getFacktAddr()%>
            </td>
            <td><%= apGIBDDStat.get(i).getArticle()%>
            </td>
            <td><%= apGIBDDStat.get(i).getCact()%>
            </td>
            <td><%= apGIBDDStat.get(i).getKol()%>
            </td>

            <td>
                <form method="post" action="/echofilter">
                    <input type="hidden" value=<%=apGIBDDStat.get(i).getLastName()%> name="lastname">
                    <input type="hidden" value=<%=apGIBDDStat.get(i).getFirstName()%> name="firstname">
                    <input type="hidden" value=<%=apGIBDDStat.get(i).getMiddleName()%> name="middlename">
                    <input type="hidden" value=<%=apGIBDDStat.get(i).getBirthDay()%> name="birthday">
                    <button type="submit">Подробно о гржданине</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }


        if (apPristStat.size() > 0) {
    %>

    <label id="lab_big">Правонарушители-рецидивисты (данные согласно базам ФССП)</label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Место жительства</th>
            <th>Статья</th>
            <th>Часть</th>
            <th>Кол-во незакрытых правонарушений</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <%
            for (int i = 0; i < apPristStat.size(); i++) {
        %>
        <tr>
            <td><%=apPristStat.get(i).getLastName()%>
            </td>
            <td><%= apPristStat.get(i).getFirstName()%>
            </td>
            <td><%= apPristStat.get(i).getMiddleName()%>
            </td>
            <td><%= apPristStat.get(i).getBirthDay()%>
            </td>
            <td><%= apPristStat.get(i).getFacktAddr()%>
            </td>
            <td><%= apPristStat.get(i).getArticle()%>
            </td>
            <td><%= apPristStat.get(i).getCact()%>
            </td>
            <td><%= apPristStat.get(i).getKol()%>
            </td>
            <td>
                <form method="post" action="/echofilter">
                    <input type="hidden" value=<%=apPristStat.get(i).getLastName()%> name="lastname">
                    <input type="hidden" value=<%=apPristStat.get(i).getFirstName()%> name="firstname">
                    <input type="hidden" value=<%=apPristStat.get(i).getMiddleName()%> name="middlename">
                    <input type="hidden" value=<%=apPristStat.get(i).getBirthDay()%> name="birthday">
                    <button type="submit">Подробно о гржданине</button>
                </form>
            </td>
        </tr>
            <%}



    %>
</div>
<%
            }
        }
    } catch (ParseException e)

    {%>
<script> alert("Дата совершения АП не заполнена ");</script>
<%
    }
%>
<%@ include file="footer.jsp" %>
</body>
