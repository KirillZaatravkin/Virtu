<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 20.11.2017
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@page import="source.*" %>
<%@ page import="source.Filter" %>
<%@ page import="java.util.List" %>
<%@ page import="javafx.scene.input.DataFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.text.ParseException" %>

<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header.jsp";</script>
<%}%>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<html>
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="css/style.css"%> </style>

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
            if(document.getElementById("ndt").value=="")
            {
                document.getElementById("ndt").value="00.00.0000";
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
            if(document.getElementById("ndt2").value=="")
            {
                document.getElementById("ndt2").value="00.00.0000";
            }
            document.getElementById("ndt2").hidden = false;
            document.getElementById("dt2").hidden = true;
        }
    </script>
</head>

<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<body>
<div id="post" >

<label></label>


    <form action="StatResediv.jsp" method="POST" name="filter">
        <div id="kol" style="height: auto">

            <div id="lab_big" >
                <label >Данные для поиска</label>
            </div>           <p></p>



            <div id="left_kol">
                <div>
                    <div><label label id="lab"> Статья </label></div>
                    <input type="text" name="article">
                </div>
            </div>

            <div id="right_kol">
                <div><label id="lab">Отчетный период</label></div>
                <input type="date" id="dt" onchange="mydate1();"/>
                <input type="text" name="d1" id="ndt" onclick="mydate();" hidden/>

                <input type="date" id="dt2" onchange="mydate12();"/>
                <input type="text" name="d2" id="ndt2" onclick="mydate2();" hidden/>
            </div>

        </div>
        <label>Сгенерировать отчет</label><input type="checkbox" name="doc"  checked value="doc">
        <button id="but_ok" type="submit" name="save" onclick="mydate();">Искать</button>
        <button id="but_ok" type="reset" >Очистить поля </button>


    </form>



    <%

        System.out.print(request.getParameter("d2"));
        String article =  null;
        StatResediv sr = new StatResediv();
        article=request.getParameter("article");
        if (article=="")
        {
            article=null;
        }
        try{
            if(request.getParameter("d1")!=null && request.getParameter("d2")!=null)
            {   SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                String STRd1 = request.getParameter("d1");
                Date d1 = format.parse(STRd1);
                java.sql.Date SQLd1 = new java.sql.Date(d1.getTime());

                String STRd2 = request.getParameter("d2");
                Date d2 = format.parse(STRd2);
                java.sql.Date SQLd2 = new java.sql.Date(d2.getTime());

                List <ApOVDStat> apOVDStats =sr.FilterStat(article, SQLd1, SQLd2);

                if(request.getParameter("doc")!=null)
                {
                    System.out.println("dfd");
                    CreateWord cw= new CreateWord();
                    cw.Resediv(apOVDStats);
                }
                %>





<label id="lab_big">Выборка за <%=SQLd1%> - <%=SQLd2%></label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Статья</th>
            <th>Кол-во правонарушений</th>
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
            <td><%= apOVDStats.get(i).getKol()%></td>
            <td> <form method="post" action="EchoFilter.jsp">
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
<%}} catch (ParseException e){

}%>
<%@ include file="footer.jsp" %>
</body>
