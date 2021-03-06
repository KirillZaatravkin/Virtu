<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Выбор страхователя</title>

    <script type="text/javascript">
        function redirect() {
            function redirect() {
                window.location.href = "/insuredsadd";
            }
        }
    </script>
</head>
<body>

<form action="/insureds" method="post">

    <label>Фамилия</label><input type="text" name="lastName" value="${lastName}">
    <label>Имя</label> <input type="text" name="firstName" value="${firstName}">
    <label>Отчество</label><input type="text" name="middleName" value="${middleName}">
    <button id="but_ok" type="submit">Найти</button>
</form>

<label id="error"><c:out value="${mass}"/></label>
<c:choose> <c:when test="${mass==null}">

    <form method="post">
        <table border=1>
            <thead>
            <tr>
                <th></th>
                <th>ФИО</th>
                <th>Паспортные данные(серия, номер)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${insuredsList}" var="listIns">
                <tr>
                    <td><input type="radio" id="id" name="id" value="<c:out value="${listIns.id}"/>"/>
                    </td>
                    <td><c:out value="${listIns.lastName} ${listIns.firsName}  ${listIns.middleName}"/></td>
                    <td><c:out value="${listIns.pasportS} , ${listIns.pasportN}"/></td>
                    <input type="hidden"
                           value="<c:out value="${listIns.lastName}  ${listIns.firsName} ${listIns.middleName}"/>"
                           id="fio"
                           name="fio"/>
                </tr>

            </c:forEach>
            </tbody>
        </table>
        <input type="button" value="выбрать"
               onclick="window.opener.resultCloseParentFIO(document.getElementById('fio').value, document.getElementById('id').value );">

    </form>

</c:when> </c:choose>
<button name="redirect" onClick="window.opener.resultCloseRedirect();">Новый страхователь</button>

</body>
</html>
