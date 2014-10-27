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

<body class="ne-11-bg" onload="parent.postMessage('Hello World','*')">
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
<main class="col-sm-6">
	<nav>
		<ul>
<% if (upcomingWebinars != null)
	{
		for (WebinarData webinar : upcomingWebinars)
		{
		List<DateAndTime> times = webinar.getDatesAndTimes();
		String timeZone = webinar.getTimeZone();	
		%>

		<div class="well ne-8-bg visible-xs">
		<h4><%=webinar.getSubject()%></h4>


		<table>
			<%for (DateAndTime when : times){
				String url = "webinarSignUp.jsp?webinarID="+when.getWebinarKey();%>
					<tr><td>
					<a href=<%=url%>><button type="input" class="btn btn-info" style="margin-bottom:5px"><%=when.getPresentableDay()%> <%=when.getPresentableDate()%> <%=when.getStartTime()%> <%=when.getTimeZone()%></button></a>
					</td></tr>

			<%}%>
		</table>
			
		</div>

        <div class="well ne-8-bg hidden-xs">
		<h4><%=webinar.getSubject()%></h4>


		<table>
			<%for (DateAndTime when : times){
				String url = "webinarSignUp.jsp?webinarID="+when.getWebinarKey();%>
					<tr><td>
					<a href=<%=url%>><button type="input" class="btn btn-info" style="margin-bottom:5px"><%=when.getDay()%> <%=when.getPresentableDate()%> <%=when.getStartTime()%>-<%=when.getEndTime()%> <%=when.getTimeZone()%></button></a>
					</td></tr>				

			<%}%>
		</table>
			
		</div>
		
		
<%
		}
	}
%>
		</ul>
	</nav>
	<br>
	<br>

	
	</main>
	<aside class="col-sm-6">
		<br>
		<br>

	<div class="jumbotron ne-cloud-bg">
			<h2 class="greeting text-primary">Webinars by Noesis Energy</h2>
			<br>
			<table>
				<tr>
					<td style="padding-right:10px;">
						<p>Noesis Energy's series of educational webinars are designed for professionals who propose and implement efficiency projects.</p>
					</td>
					<td>
						<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/utility-types-icon.png"</img>
					</td>
				</tr>
			</table>
			<br>
			<table>
				<tr>
					<td style="padding-right:10px;">
						<img src="https://noesisimg.s3.amazonaws.com/nb-images/content-type-icons/approved-folder.png"</img>
					</td>
					<td>
						<p>The webinars provide training on tools, techniques, and financing options – all to help you get more projects approved – and approved faster.</p>
					</td>
				</tr>
			</table>
			<br>
			<p><strong>Over 9,500 registrations for our webinars so far, find out why!</strong></p>

		</div>
	</aside>
	</div>
	</div> <!-- end container -->

	

<script>
$(".btn").click(function () {
    if (window != window.top) // the page is inside iframe
        window.top.scrollFrame(0); // change the position if any. 0 is top
});
</script>

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