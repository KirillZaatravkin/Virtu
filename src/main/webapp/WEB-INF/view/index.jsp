<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

    <style> <%@include file="../css/style.css"%> </style>

</head>


<body>

<div id="post">

    <a style="background-color: #e9c109" href="/" class="c">Задание 1</a>


    <p></p> <label ID="lab_name">Введите данные:</label>
    <form action="/" method="post">
        <p><input type="text" name="addr1" value="${addr1}"></p>
        <p><input type="text" name="addr2" value="${addr2}"></p>
        <p><input type="submit" value="split"></p>
    </form>

    <p><label id="lab">Результат:</label></p>


    <c:forEach items="${range}" var="ranges">
    <p><c:out value="${ranges}"/></p>
    </c:forEach>


</body>
</html>