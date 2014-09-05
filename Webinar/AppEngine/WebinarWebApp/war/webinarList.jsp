<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<html>
<head>
    <title>Upcoming webinars</title>
    
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
    <link rel="stylesheet" href="calendarview.css" type="text/css" />

    <!-- FONTS BEGIN -->
	  <link href='//fonts.googleapis.com/css?family=Kreon:300,400,700|Architects+Daughter|Pacifico' rel='stylesheet' type='text/css'>
	  <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	  <!-- FONTS END -->

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.21/angular.min.js"></script>
    <script src="prototype.js" type="text/javascript"></script>
    <script src="calendarview.js" type="text/javascript"></script>

    <style>
      div.calendar {
        max-width: 500px;
      }
      div.calendar table {
        width: 100%;
        height: 100%;
      }
    </style>

</head>

<body class="ne-11-bg">
<div class="container" style="margin:0;padding:0;">
	<header>
		
        <div class="media">
			<span class="pull-left"><img class="img-circle" src="lacey-avatar-small.png" style="background:#E1E1E1;max-width: 10em;border: .35em solid #F1F1F1;"/></span>

			<br>
			<h1 class="text-info">Upcoming Webinars</h1>
			
		</div>

		<hr>
	</header>

<%
	WebinarConnector wc = new WebinarConnector();

	List<WebinarData> upcomingWebinars = wc.getUpcomingWebinars();
%>


<div class="row">
<div class="col-md-6">
	<nav>
		<ul>
<% if (upcomingWebinars != null)
	{
		for (WebinarData webinar : upcomingWebinars)
		{
		List<DateAndTime> times = webinar.getDatesAndTimes();
		String timeZone = webinar.getTimeZone();	
		%>

		
        <div class="well ne-8-bg">
		<h4><%=webinar.getSubject()%></h4>


		<ul>
			<%for (DateAndTime when : times){
				String url = "webinarSignUp.jsp?webinarID="+when.getWebinarKey();%>
				
					<a href=<%=url%>><button type="input" class="btn btn-info" style="margin-bottom:5px"><%=when.getDay()%> <%=when.getPresentableDate()%> <%=when.getStartTime()%>-<%=when.getEndTime()%> <%=when.getTimeZone()%></button></a>
				

			<%}%>
		</ul>
			
		</div>
		
		
<%
		}
	}
%>
		</ul>
	</nav>
	<br>
	<br>

	
	</div>
	<div class="col-md-6">
		<br>
		<br>

	<div class="jumbotron ne-cloud-bg">
			<h2 class="greeting text-primary">Webinars by Noesis Energy</h2>
			<br>
			<center>
			<p>Noesis Energy's series of educational webinars are designed for professionals who propose and implement efficiency projects, providing training on tools, techniques, and financing options – all to help get more projects approved – and approved faster.</p>
			<p>We have seen over 9,500 registrations for our webinars!</p>
			</center>

		</div>
	</div>
	</div>
	</div> <!-- end container -->

	

<script>
$(".btn").click(function () {
    if (window != window.top) // the page is inside iframe
        window.top.scrollFrame(0); // change the position if any. 0 is top
});
</script>

</body>
</html>