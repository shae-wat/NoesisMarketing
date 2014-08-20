<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<html>
<head>
    <title>Upcoming webinars</title>
    
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
    <link rel="stylesheet" href="calendarview.css" type="text/css" />

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

<body style="padding:15px;">
<div class="container">
	<header>

		<br>
        <div class="media">
			<span class="pull-left">

			<img class="img-circle" src="lacey-avatar-small.png" style="background:#E1E1E1;max-width: 7em;border: .35em solid #F1F1F1;"/>

			</span>
			<td style="margin: 0; padding: 0;"> 
			<div class="media-body" style="display: block; position: relative; padding: 8px;"> <table style="margin: 0; padding: 0;"><tr style="margin: 0; padding: 0;"><td style="margin: 0; padding: 0;"> 
			<p class="well ne-5-bg">

			<span class="handwritten text-primary lead">Upcoming Webinars</span><br><span style="position: absolute; top: 20%; left: 0px; margin: 0; padding: 0;"><img src="http://noesisimg.s3.amazonaws.com/Marketing/pages/triangle-sm-blu-left.png" style="max-width: 100%; margin: 0; padding: 0;"></span> </p>

			</td>
			</tr></table></div> 
			</td> 
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

		<h4 class="text-info"><%=webinar.getSubject()%></h4>


		<ul style="list-style-position:inside;">
			<%for (DateAndTime when : times){
				String url = "webinarSignUp.jsp?webinarID="+when.getWebinarKey();%>
				<li><a href=<%=url%>><%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> <%=when.getTimeZone()%></a>
			<%}%>
			</li>
		</ul>
<%
		}
	}
%>
		</ul>
	</nav>
	
	</div>
	<div class="col-md-6">

	<div id="embeddedCalendar" style="margin-left: auto; margin-right: auto"></div>
	
	</div>
	</div>
	</div> <!-- end container -->

	
	


<script>
  function setupCalendar() {
        // Embedded Calendar
        Calendar.setup(
          {     
            parentElement: 'embeddedCalendar'
          }
        )

        
      }
  Event.observe(window, 'load', function() { setupCalendar() })
</script>


</body>
</html>