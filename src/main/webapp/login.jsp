<%@ page import="source.LoginAction" %><%--
  Created by IntelliJ IDEA.
  User: кирюха
  Date: 22.11.2017
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


    <% request.setCharacterEncoding("UTF-8");
        String login = (request.getParameter("login"));
        String password = (request.getParameter("password"));

        if (login != null && password != null) {
            LoginAction la = new LoginAction();
            String group = la.FlagLogin(login, password);
            if (group.equals("user")) {
                session.setAttribute("login", login);
                session.setAttribute("groups", la);
                String s = String.valueOf(session.getAttribute("login"));
    %>
    <script> window.location = "FindFace.jsp"; </script>
    <%
    } else {
    %>
    <script> window.location = "header.jsp"; </script>
    <%
            }
        }

    %>

</form>

</body>
</html>
