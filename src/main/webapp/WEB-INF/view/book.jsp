<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <style> <%@include file="../css/style.css"%> </style>

</head>


<body>

<div id="post">

    <a style="background-color: #e0ddd8" href="/" class="c">Задание 1</a>
    <a style="background-color: #e9c109" href="/book"  class="c">Задание 2</a>
    <p></p> <label ID="lab_name">Введите ФИО:</label>
    <form action="/book" method="post">
        <p> <input type="text" name="person" value="${person}"></p>
        <p><input type="submit" value="Показать результат"></p>
    </form>

    <p><label id="lab">Результат:</label></p>
    <div>
        <c:forEach items="${telArray}" var="tel">
            <div>
                <p><c:out value="${tel}"/></p>
            </div>
        </c:forEach>
        <p><c:out value="${error}"/></p>
    </div>
</div>
</body>
</html>