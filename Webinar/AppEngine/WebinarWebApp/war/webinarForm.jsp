<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<html>
<head>
    <!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/> -->
    <title>Upcoming webinars</title>
</head>

<body>
	<header>
		<div id="upcoming">
	        <h1>Upcoming Webinars</h1>
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
		//System.out.println("url passed = " + url);
%>
			<li>
				<a href=<%=url%>><%=webinar.getSubject()%></a>
			</li>
<%
		}
	}
%>
		</ul>
	</nav>

</body>
</html>