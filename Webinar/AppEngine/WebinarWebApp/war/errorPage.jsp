<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>
	<title>Error</title>
	
	<link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">

</head>

<body style="padding:15px;">
	<header>
		<div id="error">
		<table>
		<tr>
		<td>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/notifications-icons/error-icon.png"></img>
	    </td>
	    <td>
	        <h1 class="text-info">&nbsp;Oops! Something went wrong</h1>
	    </td>
	    </tr>
        </table>	
        </div>
		<hr>
	</header>
	
	<table>
		<tr>
		<td style="padding-right:50px; vertical-align:top;">
			<a href="/webinarList.jsp" style="margin-left:30px;"> << Back to webinar list </a>
		</td>
	    <td>
	    	<div>
			<img src="https://noesisimg.s3.amazonaws.com/nb-images/support-icons/sales-icon.png"</img>
			<br>
			<a href="https://www.noesisenergy.com/site/"><button type="input" class="btn btn-primary">Contact Sales</button></a>
			</div>
    	</td>
	    </tr>
        </table>

</body>
</html>