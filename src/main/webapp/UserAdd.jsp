<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 10.11.2017
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>


<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "header.jsp";</script>
<%}%>

<%@page import="source.*" %>
<%request.setCharacterEncoding("UTF-8");%>


<% String sId = request.getParameter("id");
    boolean flagNew;
    boolean ok = false;
    UserSee us = new UserSee();
    User user = new User();

    if (sId != null) {
        int id = Integer.valueOf(sId);

        user = us.getUser(id);
        System.out.print(user.getId());
        flagNew = false;
    } else {
        flagNew = true;
    }

    if (request.getParameter("update") != null) {
        UserSee userSee = new UserSee();
        String login = user.setLogin(request.getParameter("login"));
        String password = user.setPassword(request.getParameter("password"));
        String fio = user.setFio(request.getParameter("fio"));
        String groups = user.setGroups(request.getParameter("groups"));
        if (login != null && password != null && fio != null && groups != null) {
            userSee.updateUser(user);
            System.out.print(login);
        }

        ok = true;
    } else if (request.getParameter("save") != null) {
        UserSee userSee = new UserSee();

        String login = user.setLogin(request.getParameter("login"));
        String password = user.setPassword(request.getParameter("password"));
        String fio = user.setFio(request.getParameter("fio"));
        String groups = user.setGroups(request.getParameter("groups"));
        if (login != null && password != null && fio != null && groups != null) {
            userSee.addUser(user);
            System.out.print(login);
        }

        ok = true;
    }
    if (ok == true) {
%>
<script>window.location="index.jsp";</script>
<%
    }
%>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>


<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="css/style.css"%> </style>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>

<div id="post">
    <form action="UserAdd.jsp" accept-charset="utf-8" method="POST" name="AddUser">

        <p>
            <label id="lab">ФИО</label>
            <input type="text" name="fio" value="<%=user.getFio()%>">
        </p>

        <p>
            <label id="lab">логин</label>
            <input type="text" name="login" value="<%=user.getLogin()%>">
        </p>

        <p>
            <label id="lab">пароль</label>
            <input type="text" name="password" value="<%=user.getPassword()%>">
        </p>

        <p>
            <label id="lab">группа </label>
            <input type="text" name="groups" value="<%=user.getGroups()%>">
        </p>
        <%
            if (flagNew == true) {
        %>
        <input type="hidden" name="save" value="save">
        <%
        } else {
        %> <input type="hidden" name="update" value="update">
        <%
            }%> <input type="hidden" name="id" value="<%=user.getId()%>">
        <button type="submit" name="save">Сохранить</button>
    </form>

</div>

<%@ include file="footer.jsp" %>
</body>