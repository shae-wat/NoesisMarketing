
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
	user = wc.registerUser(webinarId, user);

%>
	<p>Thank you, <%= first_name %>  <%= last_name %>, for registering for <%=webinar.getSubject()%>
		at <%=webinar.getTimes()%>  <%=webinar.getTimeZone()%>
	</p>
	<p>An email has been sent to <%=email%> with the 
	<a href=<%=user.getJoinUrl()%>>link</a>
	to join the webinar</p>

	<p><b>Here are some more resources from Noesis Energy that may interest you:</b></p>


	<a href=https://www.noesisenergy.com/site/>Back to Noesis home page</a>
</body>
</html>