
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
	<title>Thank you for registering</title>
	
	<link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
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

	DateAndTime when = new DateAndTime(webinar.getTimes().get(0));
	String timeZone = webinar.getTimeZone();

%>

<body style="padding:15px;">
	<header>
		<div id="success">	
	        <h4 style="color:#fff;">Successful Registration for</h4>
	        <h1 class="text-info"> <%=webinar.getSubject()%> </h1>
        </div>
		<hr>
	</header>
	
	<table>
	<tr>
	<td style="width:500px; padding-right:50px; vertical-align:top;">
		<p style="color:#fff;">Thank you, <%= first_name %>  <%= last_name %>, you have registered to attend the <b><%=webinar.getSubject()%></b> webinar hosted by Noesis Energy</p>
		<p style="color:#fff;">The webinar is on <%=when.getDay()%> <%=when.getDate()%> at <%=when.getStartTime()%> <b><%=timeZone%></b></p>
		<% if(timeZone.equals("CDT")) {%>
			<p style="color:#fff;">*Please note that the start time is Central Daylight Time*</p>
		<%} else if(timeZone.equals("EDT")) {%>
			<p style="color:#fff;">*Please note that the start time is Eastern Daylight Time*</p>
		<%} else {%>
			<p style="color:#fff;">*Please note the time zone associated with this webinar*</p>
		<%}%>
		<p style="color:#fff;">An email has been sent to <%=email%> with the <a href=<%=user.getJoinUrl()%>>link</a> to join the webinar</p>
	</td>
	<td>
		<center>
		<p style="color:#fff;"><b>Here are some more resources from Noesis Energy that may interest you:</b></p>
		<br>
		</center>
		<table>
		<tr>
		<td>
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/blog-icon.png"</img>
			<br>
			<button type="input" class="btn btn-primary">Blog</button>
			</div>
			</center>
		</td>

		<td>
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/downloads-icon.png"</img>
			<br>
			<button type="input" class="btn btn-primary">Downloads</button>
			</div>
			</center>	
		</td>

		<td>
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/support-icons/sales-icon.png"</img>
			<br>
			<button type="input" class="btn btn-primary">Sales</button>
			</div>
			</center>
		</td>
		</tr>
		</table>


		<br>
		<br>
		<br>
		<br>

		<p>*Share your registration for this webinar on Twitter or LinkedIn*</p>
		<p>*Email invitation for this webinar to friends and colleagues*</p>
	</td>
	</tr>
	</table>

	<footer>
		<center>
		<hr><p style="color:#fff;">Connect with us:</p>
		<div id="socialLinks">
			<a href="https://www.noesisenergy.com/site/"><img src="noesis_logo.png" alt="company logo" width="90" height="30"></a>

			<a href="https://twitter.com/noesisenergy"><img src="twitter.png" alt="twitter logo" width="30" height="30"></a>

			<a href="https://www.linkedin.com/company/noesis-energy"><img src="linkedin.png" alt="linkedin logo" width="30" height="30"></a>
		
		<p>&copy; 2014 Noesis Energy</p>
		</div>
	</center>
	</footer>
</body>
</html>