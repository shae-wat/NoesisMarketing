<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
    <!-- <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/> -->
    <title>Registration</title>
</head>

<%
	String webinarId = request.getParameter("webinarID");
	if (webinarId == null) {
		webinarId = "default";
	}
	pageContext.setAttribute("webinarId", webinarId);

	WebinarConnector wc = new WebinarConnector();
	WebinarData webinar = wc.getWebinarInfo(webinarId);

	String action = "webinarThanks.jsp?webinarID="+ webinar.getWebinarKey();
	System.out.println(action);
	pageContext.setAttribute("action", action);

	DateAndTime when = new DateAndTime(webinar.getTimes());

%>

<body>
	<header>
		<div id="reg">
	        <h1>Register for webinar: <%=webinar.getSubject()%></h1>
        </div>
		<hr>
	</header>

<p> 
	<%=webinar.getDescription()%>
	<li><%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> EDT</li>
</p>

<form name="webinarRegistration" "${pageContext.request.contextPath}/WebinarWebAppServlet" action=<%=action%> method="POST">
	<div class="input-group formedit form-group" 
	        ng-class="{'has-error' : form.First_Name_Casual__c.$dirty && (form.First_Name_Casual__c.$error.required || form.First_Name_Casual__c.$error.maxlength)}" >
	        <span class="input-group-addon add-on ne-10"><i class="icon-user icon-fixed-width"></i></span> 
	        First Name <input type="text" 
	                name="first_name" class="form-control" ne-hint="First Name" 
	                tooltip="{{(form.First_Name_Casual__c.$dirty && (form.First_Name_Casual__c.$error.required || form.First_Name_Casual__c.$error.maxlength)) ? 'Your first name is required and must be less than 255 characters' : ''}}"         
	                ng-required="true" ng-maxlength="255" ng-model="$parent.First_Name_Casual__c">
	</div>

	<div class="input-group formedit form-group"
	        ng-class="{'has-error' : form.LastName.$dirty && (form.LastName.$error.required || form.LastName.$error.maxlength)}" >
	        <span class="input-group-addon add-on ne-10"><i class="icon-user icon-fixed-width"></i></span>
	        Last Name <input type="text" 
	                name="last_name" class="form-control" ne-hint="Last Name"
	                tooltip="{{(form.LastName.$dirty && (form.LastName.$error.required || form.LastName.$error.maxlength)) ? 'Your last name is required and must be less than 255 characters' : ''}}"         
	                ng-required="true" ng-maxlength="255" ng-model="$parent.LastName">
	</div>

	<div class="input-group formedit"
	        ng-class="{'has-error' : form.Email.$dirty && (form.Email.$error.required || form.Email.$error.maxlength || form.Email.$error.email)}" >
	        <span class="input-group-addon add-on ne-10"><i class="icon-envelope icon-fixed-width"></i></span>
	        Email <input type="email" 
	                name="email" class="form-control" ne-hint="Email"
	                tooltip="{{(form.Email.$dirty && (form.Email.$error.required || form.Email.$error.maxlength || form.Email.$error.email)) ? 'A valid email address is required' : ''}}"         
	                ng-required="true" ng-maxlength="255" ng-model="$parent.Email">
	</div>
	<input type="submit" value="Submit">
</form>

</body>
</html>