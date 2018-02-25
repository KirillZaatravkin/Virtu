<%@page import="source.*" %>
<%@ page import="source.Filter" %>
<%@ page import="java.util.List" %>
<%@ page import="javafx.scene.input.DataFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page import="java.text.ParseException" %>



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

    <form action="FindFace.jsp" method="POST" name="filter">
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
                <input type="date" id="dt" onchange="mydate1();"/>
                <input type="text" name="birthday" id="ndt" onclick="mydate();" hidden/>
            </div>

    </div>
        <p><label style="font-size: 14px"><b>*Все поля обязательны для заполнения</b></label></p>
        <button id="but_ok" type="submit" name="save" onclick="mydate();">Искать</button>
        <button id="but_ok" type="reset" >Очистить поля </button>

    </form>

    <%  request.setCharacterEncoding("UTF-8");
        String lastname = (request.getParameter("lastname"));
        String middlename = (request.getParameter("middlename"));
        String firstname = (request.getParameter("firstname"));



            try {
                if (firstname != null && middlename != null && lastname != null ) {
                String STRbirthday = request.getParameter("birthday");
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
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
    <p><label><b><%= STRbirthday%></b> года рождения. </label></p>
    <p><label>Информация из баз ОВД: </label></p>

    <table border=1>
        <thead>
        <tr>

            <th>Статья КоАП РФ</th>
            <th>Место прибывания согласно БД</th>
            <th>Место регистрации  согласно БД</th>
            <th>Дата загрузки в БД</th>
        </tr>
        </thead>
        <tbody>
        <% ;
            for (int i = 0; i < apOVDs.size(); i++) {

        %>
        <tr>
            <td><%=apOVDs.get(i).getArticle()%></td>
            <td><%= apOVDs.get(i).getFacktAddr()%></td>
            <td><%= apOVDs.get(i).getResAddr()%></td>
            <td><%= apOVDs.get(i).getDateCreate()%></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

<%} catch (ParseException e){

}%>

</div>

<%@ include file="footer.jsp" %>
</body>