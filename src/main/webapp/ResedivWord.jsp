<%@ page import="java.util.*" %>
<%@page import="source.*" %>
<%@ page import="source.Filter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="static jdk.nashorn.internal.objects.NativeString.toUpperCase" %>


<% request.setCharacterEncoding("UTF-8");
    String article =request.getParameter("article");
    String STRd2 =  (request.getParameter("d2"));
    String STRd1 = request.getParameter("d1");


    if (true) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = format.parse(STRd1);
        java.sql.Date SQLd1 = new java.sql.Date(d1.getTime());
        Date d2 = format.parse(STRd2);
        java.sql.Date SQLd2 = new java.sql.Date(d2.getTime());

       StatResediv sr = new StatResediv();
        List<ApOVDStat> ap = sr.FilterStat(article, SQLd1, SQLd2);
        if (ap.size() > 0) {
            System.out.println("dfd");
            CreateWord cw= new CreateWord();
            cw.Resediv(ap);
%>
<script>window.location ="StatResediv.jsp"</script><%
}
else
{
%>
<script>window.location ="StatResediv.jsp"</script>
<%
        }
    }%>
