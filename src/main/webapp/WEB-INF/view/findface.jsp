<%@page import="source.*" %>
<%@ page import="source.Filter" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.xmlbeans.impl.xb.xsdschema.SimpleRestrictionType" %>
<%@ page import="source.system.model.ApGIBDDStat" %>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<html>
<head>
    <script>

        function mydate() {

            document.getElementById("dt").hidden = false;
            document.getElementById("ndt").hidden = true;
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
    </script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>

<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<body>
<div id="post" style="height: 130%">

    <form action="/findface" method="POST" name="filter">
        <div id="kol">

            <div id="lab_big" >
            <label >Данные для поиска</label>
            </div>
            <p></p>
            <div id="left_kol">
                <div>
                    <div><label label id="lab"> Фамилия </label></div>
                    <input type="text" name="lastname">
                </div>

                <div>
                    <div><label label id="lab"> Имя </label></div>
                    <input type="text" name="firstname">
                </div>

                <div>
                    <div><label label id="lab"> Отчество </label></div>
                    <input type="text" name="middlename">
                </div>
            </div>

            <div id="right_kol">
                <div><label id="lab">Дата рождения</label></div>
                <div>Известна точная дата</div>	<input type="checkbox" name="on" checked value="on"  onclick="if(this.checked){this.nextSibling.style.display=''}else {this.nextSibling.style.display='none';  this.nextSibling.value='';}"><input type="date" id="dt" onchange="mydate1();"/>
                <input type="text" name="birthday" id="ndt" onclick="mydate();" hidden/>

            </div>

    </div>
        <button id="but_ok" type="submit" name="save" onclick="mydate();">Искать</button>
        <button id="but_ok" type="reset" >Очистить поля </button>

    </form>

    <%  request.setCharacterEncoding("UTF-8");
        String on=request.getParameter("on");
        List<ApOVDStat> apOVDs=new ArrayList<ApOVDStat>();
        List <ApGIBDDStat> apGIBDDs=new ArrayList<ApGIBDDStat>();
        java.sql.Date SQLbirthday=null;
        System.out.print(SQLbirthday);
        String lastname = (request.getParameter("lastname"));
        String middlename = (request.getParameter("middlename"));
        String firstname = (request.getParameter("firstname"));
        String STRbirthday = request.getParameter("birthday");
                if (firstname==""|| firstname==null){firstname ="%";}
                if (lastname==""|| lastname==null){lastname ="%";}
                if(middlename=="" || middlename==null){middlename="%";}
                if(on!=null)
                {

                    try   {
                        STRbirthday = request.getParameter("birthday");
                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        Date birthday = format.parse(STRbirthday);
                        SQLbirthday = new java.sql.Date(birthday.getTime());
                        System.out.println("hf");
                        System.out.println(SQLbirthday);
                    }catch (ParseException e)
                    {%>
                         <script> alert("Дата равна NULL ! Сними галку или корректно заполни поле");</script>
                     <%
                    }
                }
                System.out.print(request.getParameter("on"));

                if ((firstname != "%" || middlename != "%" || lastname != "%") && (on!=null))
                {
                    Filter filter = new Filter();
                    apOVDs = filter.FindFaceOVD(lastname, firstname, middlename, SQLbirthday);
                    apGIBDDs= filter.FindFaceGIBDD(lastname,firstname, middlename, SQLbirthday);
                }


              if ((firstname != "%" || middlename != "%" || lastname != "%") && (on==null))
              {
                 Filter filter = new Filter();
                 apOVDs = filter.FindFaceOVD(lastname, firstname, middlename, null);
                  apGIBDDs= filter.FindFaceGIBDD(lastname,firstname, middlename, null);
              }

if(apOVDs.size()>0)
{
   %>


    <label id="lab_big">Правонарушители из баз ОВД</label>
    <table border=1>
        <thead>
        <tr>

            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < apOVDs.size(); i++) {

        %>
        <tr>
            <td><%=apOVDs.get(i).getLastName()%></td>
            <td><%= apOVDs.get(i).getFirstName()%></td>
            <td><%= apOVDs.get(i).getMiddleName()%></td>
            <td><%= apOVDs.get(i).getBirthDay()%></td>
            <td> <form method="post" action="/echofilter">
                <input type="hidden" value=<%=apOVDs.get(i).getLastName()%> name="lastname">
                <input type="hidden" value=<%=apOVDs.get(i).getFirstName()%> name="firstname">
                <input type="hidden" value=<%=apOVDs.get(i).getMiddleName()%> name="middlename">
                <input type="hidden" value=<%=apOVDs.get(i).getBirthDay()%> name="birthday">
                <button type="submit">Подробно о грaжданине</button>
            </form>
            </td>

        </tr>
        <%

            }
        %>
        </tbody>
    </table>

<% }

if(apGIBDDs.size()>0)
{
    System.out.println("fghdf");
%>

<label id="lab_big">Правонарушители из баз ГИБДД</label>
<table border=1>
    <thead>
    <tr>

        <th>Фамилия</th>
        <th>Имя</th>
        <th>Отчество</th>
        <th>Дата рождения</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <%
        for (int i = 0; i < apGIBDDs.size(); i++) {

    %>
    <tr>
        <td><%=apGIBDDs.get(i).getLastName()%></td>
        <td><%= apGIBDDs.get(i).getFirstName()%></td>
        <td><%= apGIBDDs.get(i).getMiddleName()%></td>
        <td><%= apGIBDDs.get(i).getBirthDay()%></td>
        <td> <form method="post" action="/echofilter">
            <input type="hidden" value=<%=apGIBDDs.get(i).getLastName()%> name="lastname">
            <input type="hidden" value=<%=apGIBDDs.get(i).getFirstName()%> name="firstname">
            <input type="hidden" value=<%=apGIBDDs.get(i).getMiddleName()%> name="middlename">
            <input type="hidden" value=<%=apGIBDDs.get(i).getBirthDay()%> name="birthday">
            <button type="submit">Подробно о грaжданине</button>
        </form>
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