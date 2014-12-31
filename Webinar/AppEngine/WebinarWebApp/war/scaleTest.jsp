<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

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
	  	<form>
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