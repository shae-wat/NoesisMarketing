<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>


<%
	//URL url = new URL("https://");
	//HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	//connection.setRequestMethod("GET");
	//connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
	//connection.connect();
	String action = "webinarThanks.jsp?webinarID=8058224326129862402";
	pageContext.setAttribute("action", action);
%>

<html>
<head>
	<title>scale test</title>
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
</head>
<body class="ne-11-bg">	
<div class="container" style="margin:100;padding:0;">
	<div class="row">
	 
	  <div class="col-lg-6">
	  	<form action=<%=action%> method="POST">
	    <div class="input-group">
	      <input type="text" class="form-control" placeholder="Number of users">
	      <!-- <input type="submit"> -->
	      <span class="input-group-btn">
	        <button class="btn btn-default" type="button">Run scale test</button>
	      </span>
	      <!-- </input> -->
	    </div><!-- /input-group -->
		</form>
	  </div><!-- /.col-lg-6 -->
	</div><!-- /.row -->
</div>
</body>