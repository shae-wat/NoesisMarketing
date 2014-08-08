
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
	<title>Thank you for registering</title>
	
	<link rel="stylesheet" type="text/css" href="webinar.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.min.js"></script>
</head>

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

	DateAndTime when = new DateAndTime(webinar.getTimes());

%>

<body>
	<header>
		<div id="success">
			<center>
	        <h2 class="text-info">Successful Registration for</h2>
	        <h1 class="text-info"> <%=webinar.getSubject()%> </h1>
        </div>
		<hr>
	</header>
	<center>

	<p>Thank you, <%= first_name %>  <%= last_name %>, you have registered to attend the <b><%=webinar.getSubject()%></b> webinar hosted by Noesis Energy</p>
	<p>The webinar is on <%=when.getDays().get(0)%> <%=when.getDates().get(0)%> at <%=when.getStartTimes().get(0)%> <b>EDT</b></p>
	<p>*Please note that the start time is Eastern Daylight Time*</p>
	<p>An email has been sent to <%=email%> with the <a href=<%=user.getJoinUrl()%>>link</a> to join the webinar</p>

	<p><b>Here are some more resources from Noesis Energy that may interest you:</b></p>

	<a href="https://www.noesisenergy.com/site/content/noesis-resource-library"><img src="noesis_resources.png" alt="resource_redirect"</a>


	<!-- <p>*Share your registration for this webinar on Twitter or LinkedIn</p> -->


	<footer>
		<hr>
		<div id="socialLinks">
			<a href="https://www.noesisenergy.com/site/"><img src="noesis_logo.png" alt="company logo" width="90" height="30"></a>

			<a href="https://twitter.com/noesisenergy"><img src="twitter.png" alt="twitter logo" width="30" height="30"></a>

			<a href="https://www.linkedin.com/company/noesis-energy"><img src="linkedin.png" alt="linkedin logo" width="30" height="30"></a>
		
		<p>&copy; 2014 Noesis Energy</p>
		</div>
	</footer>
</body>
</html>