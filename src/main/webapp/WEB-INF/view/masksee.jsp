<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%
    if (session.getAttribute("login") == null) {
%>
<script> window.location = "/header";</script>
<%
    }
%>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>ИТО</title>
    <style> <%@include file="../css/style.css"%> </style>
</head>

<body>
<%@ include file="header.jsp" %>
<%@ include file="sidebar.jsp" %>
<div id="main_block">Настройка</div>

<div id="post">
    <%@ include file="vertsidebar.jsp" %>
    <p><label id="lab_big">Настройка поиска</label></p>
    <p><a id="button_href" href="/maskadd">Добавить маску</a></p>
    <label>Активные маски</label>

        <table border=1>
            <thead>
            <tr>
                <th>Прокуратура</th>
                <th>Маска</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${maskList}" var="mask">
                <tr>
                    <td><c:out value="${mask.title}"/></td>
                    <td><c:out value="${mask.mask}"/></td>
                    <td><a href="/maskupdate?id=${mask.id}">Редактировать</a> </td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>