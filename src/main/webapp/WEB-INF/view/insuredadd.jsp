<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <style> <%@include file="../css/style.css"%> </style>
</head>
<body>

<div id="post">
    <div id="block">
        <label id="lab_name">Заполнить данные о страхователе: </label>
        <form action="/insuredadd" method="post">
            <label id="error"><c:out value="${errror}"/></label>
            <p>ФИО:</p>
            Имя:<input type="text" name="firstName" value="${firstName}">
            Фамилия:<input type="text" name="lastName" value="${lastName}">
            Отчество:<input type="text" name="middleName" value="${middleName}">
            <p>Паспортные данные:</p>

            Номер: <input type="text" name="pasportn" value="${pasportn}">
            Серия: <input type="text" name="pasports" value="${pasports}">
            <p></p>
            <button id="but_ok" type="submit" value="save" name="save">Cохранить</button>
        </form>
    </div>
</div>
</body>
</html>