<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 13.06.2018
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@page import="source.*" %>
<%@ page import="source.system.dao.MaskDAO" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="source.system.model.*" %>
<%@ page import="source.system.dao.SettingsDao" %>


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
        ArticleResediv ar=new ArticleResediv();
        String sort=request.getParameter("sort");
        article=request.getParameter("article");
        cact=request.getParameter("cact");
         SettingsDao settingsDao =new SettingsDao();
            Settings sett=new Settings();
            sett= settingsDao.getSetting(2);

        int  volume=0;
        try{
            volume=Integer.parseInt(request.getParameter("volume"));
        } catch (NumberFormatException  e)
        {

        }

        String regionMask= request.getParameter("regionMask");

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


    <form action="resediv" method="POST" name="filter">
        <div id="kol">

            <div id="lab_big">
                <label>Данные для поиска</label>
            </div>

            <div id="left_kol_2">


                <div>
                    <a href="#" id="refka" title="Жми для получения справки" onclick="alert('Выбор престепления по статьям'); return false;">Вид преступление</a>
                    <select name="article">
                        <option value="12.8_12.6">Управление тр-ным средством в состоянии алкогольного опьянения (12.6 КоАП,  12.8 КоАП)</option>
                        <option value="6.1.1">Побои (6.1.1 КоАП)</option>
                        <option value="14.16">Продажа алкоголя несовершеннолетним (14.16 ч.2.1 КоАП)</option>
                        <option value="5.35.1">Неуплата алиментв (5.35.1 КоАП)</option>
                        <option value="7.27">Мелкое хищение (7.27 ч.2)</option>
                        <option value="14.17.1">Незаконная розничная продажа алкогольной и спиртосодержащей пищевой продукции (14.17.1 КоАП)</option>
                        <option value="20.2">Нарушение порядка собраний , митингов и т.д (20.2 КоАП)</option>
                        <option value="20.17">Проникновение на охраняемый объект (20.17 КоАП) </option>
                        <option value="20.33">Деятельность иностранной неправительственной организации (20.33 КоАП)</option>
                    </select>

                    <p><a id="ref" href="/statresediv" target="_blank">в списке нет нужной статьи</a></p>
                </div>


                <div>
                    <div><label id="lab">Район/город</label></div>
                    <select name="regionMask"><% for (int i = 0; i < maskList.size(); i++) { %>
                        <option value="<%=maskList.get(i).getMask()%>"><%=maskList.get(i).getTitle()%>
                        </option>
                        <%} %>
                    </select>
                </div>

                <div> <p>   <a href="#" id="refka" title="Жми для получения справки" onclick="alert('Количество преступлений( за указанный период + период, когда действует преюдицию'); return false;">Минимальное количество незакрытых нарушений</a>
                </p>
                    <select name="volume">
                        <option value="1">1</option>
                        <option selected value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                    </select>
                </div>

            </div>

            <div id="right_kol">
                <div>   <div> <p>   <a href="#" id="refka" title="Жми для получения справки" onclick="alert('Период совершения преступления. Следует учитывать, что система сама определяет период преюдиции по каждому преступлению '); return false;">Минимальное количество незакрытых нарушений</a>
                </p></div>
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
            <div> <p>   <a href="#" id="refka" title="Жми для получения справки" onclick="alert('Отчет будет помещен в папку <%=sett.getSettings()%>'); return false;">Сгенерировать отчет</a>
            </p><input type="checkbox" name="doc" checked value="doc">
        <button id="but_ok" type="submit" name="save" onclick="mydate();">Искать</button>
        <button id="but_ok" type="reset">Очистить поля</button>
    </form>

        <%
        try {

           if(request.getParameter("article") == null && request.getParameter("save")!=null)
            {%>
    <script> alert("Выберите статью для анализа");</script>
        <%}


            if (request.getParameter("d1") != null && request.getParameter("d2") != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                article =(request.getParameter("article"));


                String STRd1 = request.getParameter("d1");
                Date d1 = format.parse(STRd1);
                java.sql.Date SQLd1 = new java.sql.Date(d1.getTime());

                String STRd2 = request.getParameter("d2");
                Date d2 = format.parse(STRd2);
                java.sql.Date SQLd2 = new java.sql.Date(d2.getTime());

                List<ArticleStat> articleResedivs = new ArrayList<ArticleStat>();
                List<ApOVDStat> OVD = new ArrayList<ApOVDStat>();
                List<ApGIBDDStat> GIBDD=new ArrayList<ApGIBDDStat>();
                List<ApPristStat> PRIST = new ArrayList<ApPristStat>();
                int kolNarush = 0;
                int kolFace = 0;
                int kolRes = 0;

                    articleResedivs = ar.FilterStat(article, SQLd1, SQLd2, sort, "found", cact, regionMask, volume);
                    kolNarush =  ar.KolNarush(article, regionMask, SQLd1, SQLd2, false);
                    kolFace = kolFace + ar.KolFace(article, regionMask, SQLd1, SQLd2, false);
                    kolRes = articleResedivs.size();


                if (request.getParameter("doc") != null) {
                    CreateWord cw = new CreateWord();
                    cw.Resediv(articleResedivs,OVD, GIBDD,PRIST, kolNarush, kolFace, kolRes);
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
        <% if (articleResedivs.size() > 0) {%>
    <label id="lab_big">Правонарушители-рецидивисты </label>
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
            for (int i = 0; i < articleResedivs.size(); i++) {

                String c = articleResedivs.get(i).getCact();
                if (c == null) c = "--";


        %>
        <tr>
            <td><%=articleResedivs.get(i).getLastName()%>
            </td>
            <td><%= articleResedivs.get(i).getFirsName()%>
            </td>
            <td><%=articleResedivs.get(i).getMiddleName()%>
            </td>
            <td><%= articleResedivs.get(i).getBirthday()%>
            </td>
            <td><%= articleResedivs.get(i).getFacktAddr()%>
            </td>
            <td><%= article%>
            </td>
            <td><%= c%>
            </td>
            <td><%= articleResedivs.get(i).getKol()%>
            </td>
            <td>
                <form method="post" action="/echofilter">
                    <input type="hidden" value=<%=articleResedivs.get(i).getLastName()%> name="lastname">
                    <input type="hidden" value=<%=articleResedivs.get(i).getFirsName()%> name="firstname">
                    <input type="hidden" value=<%=articleResedivs.get(i).getMiddleName()%> name="middlename">
                    <input type="hidden" value=<%=articleResedivs.get(i).getBirthday()%> name="birthday">
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
           }
    } catch (ParseException e)

    {%>
    <script> alert("Дата совершения АП не заполнена ");</script>
        <%
    }
%>
    <%@ include file="footer.jsp" %>
</body>
