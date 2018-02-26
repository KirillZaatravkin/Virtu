<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 15.11.2017
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "/header";</script>
<%}%>

<%@ page import="java.util.*" %>
<%@page import="source.*" %>
<%@ page import="source.Filter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="static jdk.nashorn.internal.objects.NativeString.toUpperCase" %>
<%@ page import="source.system.model.Settings" %>
<%@ page import="source.system.dao.SettingsDao" %>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>

<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<body>

<div id="post">

    <%

        request.setCharacterEncoding("utf-8");
try{
        String lastname = toUpperCase(request.getParameter("lastname"));
        String firstname = toUpperCase(request.getParameter("firstname"));
        String middlename = toUpperCase(request.getParameter("middlename"));
        String STRbirthday = request.getParameter("birthday");
         SettingsDao ser=new SettingsDao();
         Settings sett= ser.getSetting(2);

        if (STRbirthday != null && firstname != null && middlename != null && lastname != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthday = format.parse(STRbirthday);
            java.sql.Date SQLbirthday = new java.sql.Date(birthday.getTime());
            Filter filter = new Filter();
            List<ApOVD> apOVDs = filter.FilterApOVD(lastname, firstname, middlename, SQLbirthday);


    %>
    <label id="lab_name"><%= lastname%>
    </label>
    <label id="lab_name"><%= firstname%>
    </label>
    <label id="lab_name"><%= middlename%>
    </label>
    <p><label><b><%= STRbirthday%>
    </b> года рождения. </label></p>
    <p><label>Информация из баз ОВД: </label></p>

    <table border=1>
        <thead>
        <tr>
            <th>Статья КоАП РФ</th>
            <th>Дата АП</th>
            <th>Место прибывания согласно БД</th>
            <th>Место регистрации согласно БД</th>
            <th>Дата загрузки в БД</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < apOVDs.size(); i++) {

        %>
        <tr>

            <td><%=apOVDs.get(i).getArticle()%>
            </td>
            <td><%=apOVDs.get(i).getDateP()%>
            </td>
            <td><%= apOVDs.get(i).getFacktAddr()%>
            </td>
            <td><%= apOVDs.get(i).getResAddr()%>
            </td>
            <td><%= apOVDs.get(i).getDateCreate()%>
            </td>
        </tr>
        <%
            }
       %>
        <form action="/createword" method="post">
            <input type="hidden" name="list" value="1">
            <input type="hidden" name="lastname" value=<%=lastname%>>
            <input type="hidden" name="firstname" value=<%=firstname%>>
            <input type="hidden" name="middlename" value=<%=middlename%>>
            <input type="hidden" name="birthday" value=<%=STRbirthday%>>
            <button type="submit"> Сгенерировать отчет</button>
            <label id="lab">Папка , в которую будет помещен отчет : <%=sett.getSettings()%></label>
        </form>

        <%

            }
        %>
        </tbody>
    </table>

</div>
<%@ include file="footer.jsp" %>
</body>
</html>
<%}catch (java.lang.NullPointerException e)
{
    %>
<script>window.location("/statresediv")</script>
<%
}%>