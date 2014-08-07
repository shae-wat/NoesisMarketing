<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<html>
<head>
    <!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/> -->
    <title>Upcoming webinars</title>
    
    <link rel="stylesheet" type="text/css" href="webinar.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.min.js"></script>
</head>

<body style="padding:15px;">
	<header>
		<div id="upcoming">
	        <h1 class="text-info">Upcoming Webinars</h1>
        </div>
		<hr>
	</header>

<%
	WebinarConnector wc = new WebinarConnector();

	List<WebinarData> upcomingWebinars = wc.getUpcomingWebinars();

%>

	<nav>
		<ul>
<% if (upcomingWebinars != null)
	{
		for (WebinarData webinar : upcomingWebinars)
		{
		String url = "webinarSignUp.jsp?webinarID="+webinar.getWebinarKey();
		DateAndTime when = new DateAndTime(webinar.getTimes());
%>
			<li>
				<a href=<%=url%>><%=webinar.getSubject()%></a><p> <%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> EDT</p>
			</li>
<%
		}
	}
%>
		</ul>
	</nav>

</body>
</html>