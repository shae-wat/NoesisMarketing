
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
<title>Thank you for registering</title>
</head>
<body>
<center>
<%
	String webinarId = request.getParameter("webinarID");
	pageContext.setAttribute("webinarId", webinarId);

	WebinarConnector wc = new WebinarConnector();
	WebinarData webinar = wc.getWebinarInfo(webinarId);

	String first_name = request.getParameter("first_name");
	String last_name = request.getParameter("last_name");
	String email = request.getParameter("email");
	

	WebinarUser user = new WebinarUser(first_name,last_name,email);
	wc.registerUser(webinarId, user);

%>
<p>Thank you, <%= first_name %>  <%= last_name %>, for registering for <%=webinar.getSubject()%>
</p>
<ul>
<li><p><b>First Name:</b>
   <%= first_name %>
</p></li>
<li><p><b>Last  Name:</b>
   <%= last_name %>
</p></li>
<li><p><b>Email:</b>
   <%= email %>
</p></li>
</ul>
</body>
</html>