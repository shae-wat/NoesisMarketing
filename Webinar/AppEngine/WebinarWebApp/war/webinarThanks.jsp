
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>
<%@ page import="com.noesis.marketo.*" %>

<html>
<head>
	<title>Thank you for registering</title>
	
	<link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.min.js"></script>
    <!-- FONTS BEGIN -->
	  <link href='//fonts.googleapis.com/css?family=Kreon:300,400,700|Architects+Daughter|Pacifico' rel='stylesheet' type='text/css'>
	  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	  <!-- FONTS END -->

	  <%
		String webinarId = request.getParameter("webinarID");
		pageContext.setAttribute("webinarId", webinarId);
		String webinarUrl="/webinarSignUp.jsp?webinarID="+webinarId;
		pageContext.setAttribute("webinarUrl", webinarUrl);

		WebinarConnector wc = new WebinarConnector();
		WebinarData webinar = wc.getWebinarInfo(webinarId);

		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		
		String phone = request.getParameter("Phone");
		String company = request.getParameter("Company");
		String location = request.getParameter("Company_Headquarter_State__c");
		String biztype = request.getParameter("Job_Category__c");
		String companySize = request.getParameter("Company_Size__c");
		String revenue = request.getParameter("Annual_Revenue_Picklist__c");
		String customQuestion = request.getParameter("customQ");
		String webinarName = webinar.getSubject();

		WebinarUser user = new WebinarUser(first_name,last_name,email);
		user = wc.registerUser(webinarId, user);

		DateAndTime when = new DateAndTime(webinar.getTimes().get(0), webinar.getTimeZone());
		String timeZone = webinar.getTimeZone();
		
		MarketoConnector mc = new MarketoConnector();
		mc.addLead(first_name, last_name, email, phone, company, location, biztype, companySize, revenue, customQuestion, webinarName);

	%>


</head>


<body class="ne-11-bg" onload="parent.postMessage('Hello World','*')">

	<div class="container" style="padding:0;margin:0;">
	<!-- <header>
		<div id="success">	
	        <h4 style="color:#fff;">Successful Registration for</h4>
	        <h1 class="text-info"> <%=webinar.getSubject()%> </h1>
        </div>
		<hr>
	</header> -->
	
	<div class="row">
	<div class="col-sm-6">

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
	<div class="col-sm-6">
		<br>
		<br>
		<center><h3 class="greeting text-primary">Next Steps</h3></center>
		<br>
		<br>
	
		<h3 class="handwritten">Share your registration with your social networks</h3>
		<br>
		<center>
			<table>
			<tr>
				<td style="padding-right:20px;">

				<a href="https://twitter.com/share" class="twitter-share-button" data-url="<%=webinarUrl%>" data-text="I just registered for the <%=webinar.getSubject()%> webinar by @noesisenergy" 	data-count="none">Tweet</a>
				<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>
				</td>
				<td>
					<script src="//platform.linkedin.com/in.js" type="text/javascript">
					lang: en_US</script>
					<script type="IN/Share" data-url="<%=webinarUrl%>"></script>
				</td>
			</tr>
			
			</table>
		</center>
		<br>

		<br>
		<h3 class="handwritten">Check out other resources by Noesis Energy</h3>
		<center>
		<br>
		<table>
		<tr>
		<td style="padding-right:10px;">
			<center>
			<div>
			<a target="_blank" href="https://www.noesisenergy.com/site/noesis-blog"><img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/discussion-icon.png"</img></a>
			<br>
			<p>Blog</button></p>
			</div>
			</center>
		</td>

		<td style="padding-right:10px;">
			<center>
			<div>
			<a target="_blank" href="https://www.noesisenergy.com/site/content/noesis-resource-library"><img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/reports-icon.png"</img></a>
			<br>
			<p>Resource Library</p>
			</div>
			</center>	
		</td>

		<td style="padding-right:10px;">
			<center>
			<div>
			<a target="_blank" href="https://www.noesisenergy.com/site/content/contact-us"><img src="https://noesisimg.s3.amazonaws.com/nb-images/support-icons/sales-icon.png"</img></a>
			<br>
			<p>Contact Us</p>
			</div>
			</center>
		</td>
		</tr>
		</table>
		 <br>
            <a href="/webinarList.jsp"> << Back to webinar list </a>
            <br>
            <br>
		</center>


		
	</div>
</div>

	
	</div> <!-- end container -->

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-25040971-4', 'auto');
  ga('send', 'pageview');

</script>
	
</body>
</html>