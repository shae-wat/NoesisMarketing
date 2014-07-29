<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<html>
<head>
    <!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/> -->
    <title>Registration</title>
</head>

<body>
	<header>
		<div id="reg">
	        <h1>Register for webinar</h1>
        </div>
		<hr>
	</header>

<%
	String webinarId = request.getParameter("webinarID");
	if (webinarId == null) {
		webinarId = "default";
	}
	pageContext.setAttribute("webinarId", webinarId);

	WebinarConnector wc = new WebinarConnector();
	WebinarData webinar = wc.getWebinarInfo(webinarId);



%>

<p> ${fn:escapeXml(webinarId)} </p>

</body>
</html>