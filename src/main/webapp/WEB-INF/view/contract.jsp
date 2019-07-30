<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <style> <%@include file="../css/style.css"%> </style>
    <style> <%@include file="../css/jquery-ui.css"%> </style>
    <title>Контракт</title>
    <script type="text/javascript" src="/js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.12.1js.js"></script>
    <script>
        $(function () {
            $("#datepicker").datepicker({dateFormat: "dd.mm.yy"}).val()
        });

        $(function () {
            $("#datepicker2").datepicker({dateFormat: "dd.mm.yy"}).val()
        });
    </script>

    <script type="text/javascript">
        var children_window;
        function openChildren() {
            var params = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes";
            children_window = window.open("/insureds", "Children", params);
        }


        function resultCloseParentFIO(fioIns, idIn) {
            children_window.close();
            input = document.getElementById('fio');
            fio.value = fioIns;
            input = document.getElementById('idIns');
            idIns.value = idIn;
        }
        function resultCloseRedirect() {
            children_window.close();
            window.location.href = "/insuredadd";
        }

    </script>

</head>
<body>

<div id="post">


    <form action="/contract" method="post">
        <label ID="lab_name">Расчет</label>
        <div id="block">
            <div id="left_block">
                <p>Страховая сумма<input type="text" name="sum" value="${sum}">
                    <label id="error"><c:out value="${errorSum}"/></label></p>
                <div>Тип недвижимости
                    <select name="property">
                        <c:forEach items="${propertyList}" var="pr">
                            <option
                                    <c:choose> <c:when test="${pr.id==property}">selected </c:when> </c:choose>
                                    value="<c:out value="${pr.id}"/>">
                                <c:out value="${pr.title}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <p>Год постройки
                    <input type="text" name="year" value="${year}"><label id="error"><c:out
                            value="${errorYear}"/></label>
                </p>
                <p>Площадь
                    <input type="text" name="sq" value="${sq}"> м.кв.<label id="error"><c:out
                            value="${errorSq}"/></label>
                </p>
            </div>

            <div id="right_block">
                Срок действия
                c <input type="text" value="${date1}" placeholder="01.01.2017" name="date1" id="datepicker">
                по <input type="text" value="${date2}" placeholder="01.01.2018" name="date2" id="datepicker2">
                <label id="error"><c:out value="${errorDat}"/></label>

                <p><label id="textar">Премия:</label><label id="lab"><c:out value="${prize}"/></label></p>
                <p><label id="textar">Дата расчета:</label><label id="lab"><c:out value="${date_calc}"/></label></p>
            </div>
        </div>

        <div id="block">
            <div>
                <button id="but_ok" type="submit" value="calc" name="calc">Расчитать премию</button>

            </div>
        </div>


        <div id="block">
            <div><p>Страхователь:</p>
                ФИО<input type="text" readonly id="fio" name="fio" value="${fio}">
                <input type="hidden" id="idIns" name="idIns" value="${idIns}">
                <label id="error"><c:out value="${errorIns}"/></label>
            </div>
            <input type="button" value="Выбор страхователя" onclick="openChildren();">
        </div>

        <div id="block">
            <p>Адрес недвижимости:</p>
            Страна: <input type="text" name="land" value="${land}">
            Область:<input type="text" name="oblast" value="${oblast}">
            Район: <input type="text" name="raion" value="${raion}">
            Пункт:<input type="text" name="punkt" value="${punkt}">
            Улица: <input type="text" name="street" value="${street}">
            Строение: <input type="text" name="stroenie" value="${stroenie}">
            Дом:<input type="text" name="house" value="${house}">
            Квартира:<input type="text" name="room" value="${room}">
        </div>
        <input type="hidden" id="id" name="id" value="${id}">
        <button id="but_ok" type="submit" value="save" name="save">Cохранить</button>
        <input type="reset" value="Очистить поля формы">
    </form>
</div>
</body>
</html>
