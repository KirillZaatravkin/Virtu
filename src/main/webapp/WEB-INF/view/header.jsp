<%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 30.10.2017
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="utf-8" %>
<% request.setCharacterEncoding("utf-8");
    String s = request.getParameter("exit");

    if (s != null) {
        session.setAttribute("login", null);
        session.setAttribute("group", null);

    }
%>

<div id="header">

    <div style="width: 100%;">
        <div style=" float: right; width: 200px;"><img src="/media/logo.png"></div>
        <div style="float: right; max-width: 750px;"><h1>База АП по Псковской области.</h1></div>
        <% if (session.getAttribute("login") == null) { %>
        <form action="/login" method="post">
            Логин <input type="text" name="login">
            Пароль <input type="password" name="password">
            <button type="submit"> Войти</button>

        </form>
        <%
            }
            if (session.getAttribute("login") != null) { %>
        <label style="color: #fff7f4"> Здравствуйте, <%= session.getAttribute("login")%>
        </label>
        <form method="post" action="header.jsp">
            <input type="hidden" name="exit" value="exit">
            <button type="submit" name="save" id="but_ok">Выйти</button>
        </form>
        <%}%>
    </div>
</div>
