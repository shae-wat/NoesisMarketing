
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
	<title>Thank you for registering</title>
	
	<link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
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

	DateAndTime when = new DateAndTime(webinar.getTimes().get(0), webinar.getTimeZone());
	String timeZone = webinar.getTimeZone();

%>

<body style="padding:15px;">
	<div class="container">
	<!-- <header>
		<div id="success">	
	        <h4 style="color:#fff;">Successful Registration for</h4>
	        <h1 class="text-info"> <%=webinar.getSubject()%> </h1>
        </div>
		<hr>
	</header> -->
	
	<div class="row">
	<div class="col-md-6">

		<div class="jumbotron ne-cloud-bg">
			<h2 class="text-info">Successful Registration for <%=webinar.getSubject()%> </h2>

			<p>Thank you, <%= first_name %>  <%= last_name %>, you have registered to attend the <b><%=webinar.getSubject()%></b> webinar hosted by Noesis Energy</p>
			<p>The webinar is on <%=when.getDay()%> <%=when.getDate()%> at <%=when.getStartTime()%> <b><%=timeZone%></b></p>
			<% if(timeZone.equals("CDT")) {%>
				<p>*Please note that the start time is Central Daylight Time*</p>
			<%} else if(timeZone.equals("EDT")) {%>
				<p>*Please note that the start time is Eastern Daylight Time*</p>
			<%} else {%>
				<p>*Please note the time zone associated with this webinar*</p>
			<%}%>
			<p>An email has been sent to <%=email%> with the <a href=<%=user.getJoinUrl()%>>link</a> to join the webinar</p>

		</div>

	</div>
	<div class="col-md-6">
		<center>
			<br>
		<h3 style="color:#fff;"><b>Here are some more resources from Noesis Energy that may interest you:</b></h3>
		<br>
		<br>
		<table>
		<tr>
		<td style="padding-right:10px;">
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/blog-icon.png"</img>
			<br>
			<a href="https://www.noesisenergy.com/site/noesis-blog"><button type="input" class="btn btn-primary">Blog</button></a>
			</div>
			</center>
		</td>

		<td style="padding-right:10px;">
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/downloads-icon.png"</img>
			<br>
			<a href="https://www.noesisenergy.com/site/content/noesis-resource-library"><button type="input" class="btn btn-primary">Resource Library</button></a>
			</div>
			</center>	
		</td>

		<td style="padding-right:10px;">
			<center>
			<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/support-icons/sales-icon.png"</img>
			<br>
			<a href="https://www.noesisenergy.com/site/content/contact-us"><button type="input" class="btn btn-primary">Contact Us</button></a>
			</div>
			</center>
		</td>
		</tr>
		</table>
		</center>

		<br>
		<br>
		<br>

		<center>
			<p style="color:#fff;">Share your registration with your social networks</p>
		<table>
		<tr>
		<td>
			
			<img src="linkedinShare.png"></img>
			
		</td>
		<td>
			
			<img src="twitterShare.png"></img>
			
		</td>
		</tr>
		</table>
		</center>

	
		<br>
		<br>
		<center>	
			<p style="color:#fff;">Email invitation for this webinar to friends and colleagues</p>
			<img src="email.png"></img>
		</center>


		
	</div>
</div>

	<footer>
		<center>
		<hr><p style="color:#fff;">Connect with us:</p>
		<div id="socialLinks">
			<a href="https://www.noesisenergy.com/site/"><img src="noesis_logo.png" alt="company logo" width="90" height="30"></a>


			<a href="https://twitter.com/noesisenergy" class="twitter-follow-button" data-show-count="false">Follow @noesisenergy</a>
			<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>

			<script src="//platform.linkedin.com/in.js" type="text/javascript">
  			lang: en_US </script> <script type="IN/FollowCompany" data-id="460089" data-counter="none"></script>
		
		<p>&copy; 2014 Noesis Energy</p>
		</div>
	</center>
	</footer>
	</div> <!-- end container -->
</body>
</html>