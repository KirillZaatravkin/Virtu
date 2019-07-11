<%@ page import="source.system.Parse" %><%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 29.12.2018
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <style> <%@include file="../css/style.css"%> </style>

</head>


<body>

<%
    String s = request.getParameter("s");
    String s1;
    Parse parse = new Parse();
    if (s == null)  s1 = "";
    else s1 = s;

%>
<div id="post">

    <a style="background-color: #e9c109"  href="/" class="c">log:pass--></a>
    <a href="/splitid" class="c">Убраnь ID--></a>
    <a href="/refka" class="c">ссылки--></a>
    <a  href="/redirect" class="c">Добавить redirect--></a>
    <a href="/split" class="c">Убрать тест перед ссылкой--></a><p></p>

    <label ID="lab_name">Исходные данные:</label>
    <form action="/" method="post">
        <textarea id="textar" name="s"><%=s1%></textarea>
        <p><input type="submit" value="split"></p>
    </form>
    <p><label id="lab">log:pass</label></p>
    <div id="logpass">

        <% if (s != null) {
            String[] logPass = parse.logPass(s, 0);
            for (int i = 0; i < logPass.length; i++) { %>
        <p> <%= logPass[i]%></p> <%
            }
        }
    %>
    </div>


    <p><label id="lab">ФИО</label></p>
    <div id="logpass">

        <% if (s != null) {
            String[] logPass = parse.logPass(s, 1);
            for (int i = 0; i < logPass.length; i++) { %>
        <p> <%= logPass[i]%></p> <%
            }
        }
    %>
    </div>

    <p><label id="lab">id</label></p>
    <div id="logpass">

        <% if (s != null) {
            String[] id = parse.logPass(s, 2);
            String tempId = "";
            for (int i = 0; i < id.length; i++) {
                tempId = tempId + id[i];
            }
        %>
        <label><%=tempId%>
        </label>
        <%

            }
        %>
    </div>



    <p><label id="lab">id</label></p>
    <div id="logpass">

        <% if (s != null) {
            String[] id = parse.logPass(s, 2);

            for (int i = 0; i < id.length; i++) { %>
        <p><%=id[i]%>
        </p>
        <%
                }
            }
        %>
    </div>



    <p><label id="lab">token</label></p>
    <div id="logpass">
        <% if (s != null) {
            String[] token = parse.logPass(s, 7);

            for (int i = 0; i < token.length; i++) { %>
        <p><%=token[i]%>
        </p>
        <%
                }
            }
        %>

    </div>

</div>

</body>
</html>