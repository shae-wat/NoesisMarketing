<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/> -->
</head>

<body>

<%
    String webinarName = request.getParameter("webinarName");
    if (webinarName == null) {
        webinarName = "default";
    }
    pageContext.setAttribute("webinarName", webinarName);
%>


<form action="/webinarForm.jsp" method="get">
    <div><input type="submit" value="${fn:escapeXml(webinarName)}"/></div>
    <div><input type="submit" value="Switch Guestbook"/></div>
</form>

</body>
</html>