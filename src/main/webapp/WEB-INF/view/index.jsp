<%@ page import="source.system.service.InsuredsService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <style> <%@include file="../css/style.css"%> </style>
    <script type="text/javascript">
        function go() {
            window.location.href = "/contract";
        }
        function update(id) {
            window.location.href = "/contract?id="+id;
        }
    </script>

</head>


<body>

<div id="post">

    <button name="go" onClick="go();">Cоздать новый контракт</button>

    <table border=1>
        <thead>
        <tr>
            <th>Номер договора</th>
            <th>Премия</th>
            <th>Дата начала договора</th>
            <th>Дата окончания договора</th>
            <th>ФИО</th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${contractList}" var="l">
            <tr>
                <td><c:out value="${l.id}"/></td>
                <td><c:out value="${l.prize}"/></td>
                <td><c:out value="${l.date1}"/></td>
                <td><c:out value="${l.date2}"/></td>
                <td><c:out value="${l.insured.lastName} ${l.insured.firsName} ${l.insured.middleName}"/></td>
                <td><button name="go" onClick="update(<c:out value="${l.id}"/>);">Редактировать</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>