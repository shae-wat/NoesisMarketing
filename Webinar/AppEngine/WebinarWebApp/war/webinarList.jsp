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

<body>
<div class="container">
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

		
        <div class="well">
		<h4 class="lead text-primary"><%=webinar.getSubject()%></h4>


		<ul style="list-style-position:inside;">
			<%for (DateAndTime when : times){
				String url = "webinarSignUp.jsp?webinarID="+when.getWebinarKey();%>
				<li><a href=<%=url%>><%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> <%=when.getTimeZone()%></a>
			<%}%>
			</li>
		</ul>
		</div>
		
		
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