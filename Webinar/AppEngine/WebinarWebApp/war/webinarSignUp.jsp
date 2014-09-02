<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/jquery.validate.min.js"></script>
    
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="https://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">
    <!-- FONTS BEGIN -->
      <link href='//fonts.googleapis.com/css?family=Kreon:300,400,700|Architects+Daughter|Pacifico' rel='stylesheet' type='text/css'>
      <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
      <!-- FONTS END -->


      <%
    
        String webinarId = request.getParameter("webinarID");
    
        pageContext.setAttribute("webinarId", webinarId);
        WebinarData webinar = null;
        String customQuestion = null;

        try{
            WebinarConnector wc = new WebinarConnector();
            webinar = wc.getWebinarInfo(webinarId);
            customQuestion = wc.getCustomQuestion(webinarId);
            pageContext.setAttribute("customQuestion", customQuestion);
        } catch (NullPointerException e){
            // New location to be redirected
           String errorPage = new String("errorPage.jsp");
           
           response.sendRedirect(errorPage); 
        }
    
        String action = "webinarThanks.jsp?webinarID="+ webinar.getWebinarKey();
        System.out.println(action);
        pageContext.setAttribute("action", action);

        DateAndTime when = new DateAndTime(webinar.getTimes().get(0), webinar.getTimeZone());
    %>
    
    <meta name="twitter:card" content="summary">
    <meta name="twitter:title" content="I just registered for <%=webinar.getSubject()%>">
    <meta name="twitter:description" content="<%=webinar.getDescription()%>">
    <meta name="twitter:image:src" content="https://noesisimg.s3.amazonaws.com/nb-images/brand/NoesisGlyph.png">
</head>



<body class="ne-11-bg">
<div class="container">
<header>
    <table><tr>
    <td style="padding-right:50px;">
	<div id="reg">
        <h1 class="text-info"><%=webinar.getSubject()%></h1>
    </div>
    <h4>Join us on <%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> <%=webinar.getTimeZone()%>
    </h4>
    </td>
    <td>
    <img src="webinarPic.jpg"></img>
    </td>
    </tr></table>
    <hr>
    
</header>

<div class="row">
<div class="col-md-6">
    <div class="well ne-6-bg">
              <form id="regForm" name="webinarRegistration" "${pageContext.request.contextPath}/WebinarWebAppServlet" action=<%=action%> method="POST" class="bs-example form-horizontal">
                <fieldset>
                  <h3 class="ne-11 handwritten" style="margin-top: 0px;">Register for this webinar : </h3>
                    <span style="font-size:12px; color:#fff;"> (all fields required)</span>
                    <hr class="dashed-line-lt" /><br>

                  <div class="form-group col-lg-10">
                  <div class="input-group formedit ng-scope margin-bottom-sm" ng-class="{'has-error' : form.First_Name_Casual__c.$dirty && (form.First_Name_casual__c.$error.required || form.First_Name_Casual__c.$error.maxlangth)}">
                      <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
                      <input id="firstName" type="text" 
                        name="first_name" class="form-control" ng-required="true" ng-maxlength="255" ng-model="$parent.First_Name_Casual__c" type="text" placeholder="First Name">
                    </div>
                  </div>

                  <div class="form-group col-lg-10">
                  <div class="input-group formedit ng-scope margin-bottom-sm" ng-class="{'has-error' : form.Last_Name_Casual__c.$dirty && (form.Last_Name_casual__c.$error.required || form.Last_Name_Casual__c.$error.maxlangth)}">
                    <span class="input-group-addon"><i class="fa fa-user fa-fw"></i></span>
                    
                      <input id="lastName" type="text" 
                        name="last_name" class="form-control ng-scope ng-invalid ng-invalid-required ng-valid-maxlength ng-dirty" tooltip="Your last name is required and must be less that 255 characters" ng-required="true" ng-maxlength="255" ng-model="$parent.LastName" type="text" placeholder="Last Name">
                  </div>
                  </div>

                  <div class="form-group col-lg-10">
                    <div class="input-group margin-bottom-sm">
                      <span class="input-group-addon"><i class="fa fa-envelope-o fa-fw"></i></span>
                      <input id="email" type="email" 
                        name="email" class="form-control" ng-required="true" ng-maxlength="255" ng-model="$parent.Email" type="text" placeholder="Email">
                    </div>
                  </div>

                  <div class="form-group col-lg-10">
                    <div class="input-group margin-bottom-sm">
                      <span class="input-group-addon"><i class="fa fa-phone fa-fw"></i></span>
                      <input id="phone" type="tel" 
                        name="Phone" class="form-control" ng-required="true" ng-minlength="10" ng-maxlength="16" ng-model="$parent.Phone" type="text" placeholder="Phone (xxx-xxx-xxxx)">
                    </div>
                  </div>

                  <div class="form-group col-lg-10">
                    <div class="input-group margin-bottom-sm">
                      <span class="input-group-addon"><i class="fa fa-building-o fa-fw"></i></span>
                      <input id="companyName" type="text" 
                        name="Company" class="form-control" ng-required="true" ng-maxlength="255" ng-model="$parent.Company" type="text" placeholder="Company Name">
                    </div>
                  </div>

                  <div class="form-group col-lg-10">
                        <select id="companyLocation" name="Company_Headquarter_State__c" class="form-control"
                            ng-model="$parent.Company_Headquarter_State__c"
                            ng-required="true"
                            tooltip="{{(form.Company_Headquarter_State__c.$dirty && form.Company_Headquarter_State__c.$error.required) ? 'Select a valid Company Location' : ''}}">
                            <option value="">Select Company Location</option>
                            <option value="Alaska">USA/Alaska</option>
                            <option value="Alabama">USA/Alabama</option>
                            <option value="Arkansas">USA/Arkansas</option>
                            <option value="Arizona">USA/Arizona</option>
                            <option value="California">USA/California</option>
                            <option value="Colorado">USA/Colorado</option>
                            <option value="Connecticut">USA/Connecticut</option>
                            <option value="Delaware">USA/Delaware</option>
                            <option value="Florida">USA/Florida</option>
                            <option value="Georgia">USA/Georgia</option>
                            <option value="Hawaii">USA/Hawaii</option>
                            <option value="Iowa">USA/Iowa</option>
                            <option value="Idaho">USA/Idaho</option>
                            <option value="Illinois">USA/Illinois</option>
                            <option value="Indiana">USA/Indiana</option>
                            <option value="Kansas">USA/Kansas</option>
                            <option value="Kentucky">USA/Kentucky</option>
                            <option value="Louisiana">USA/Louisiana</option>
                            <option value="Massachusetts">USA/Massachusetts</option>
                            <option value="Maryland">USA/Maryland</option>
                            <option value="Maine">USA/Maine</option>
                            <option value="Michigan">USA/Michigan</option>
                            <option value="Minnesota">USA/Minnesota</option>
                            <option value="Missouri">USA/Missouri</option>
                            <option value="Mississippi">USA/Mississippi</option>
                            <option value="Montana">USA/Montana</option>
                            <option value="North Carolina">USA/North Carolina</option>
                            <option value="North Dakota">USA/North Dakota</option>
                            <option value="Nebraska">USA/Nebraska</option>
                            <option value="New Hampshire">USA/New Hampshire</option>
                            <option value="New Jersey">USA/New Jersey</option>
                            <option value="New Mexico">USA/New Mexico</option>
                            <option value="Nevada">USA/Nevada</option>
                            <option value="New York">USA/New York</option>
                            <option value="Ohio">USA/Ohio</option>
                            <option value="Oklahoma">USA/Oklahoma</option>
                            <option value="Oregon">USA/Oregon</option>
                            <option value="Pennsylvania">USA/Pennsylvania</option>
                            <option value="Puerto Rico">USA/Puerto Rico</option>
                            <option value="Rhode Island">USA/Rhode Island</option>
                            <option value="South Carolina">USA/South Carolina</option>
                            <option value="South Dakota">USA/South Dakota</option>
                            <option value="Tennessee">USA/Tennessee</option>
                            <option value="Texas">USA/Texas</option>
                            <option value="Utah">USA/Utah</option>
                            <option value="Virginia">USA/Virginia</option>
                            <option value="Vermont">USA/Vermont</option>
                            <option value="Washington">USA/Washington</option>
                            <option value="Washington DC">USA/Washington DC</option>
                            <option value="Wisconsin">USA/Wisconsin</option>
                            <option value="West Virginia">USA/West Virginia</option>
                            <option value="Wyoming">USA/Wyoming</option>
                            <option value="US Virgin Islands">US Virgin Islands</option>
                            <option value="Alberta">Canada/Alberta</option>
                            <option value="British Columbia">Canada/British
                                Columbia</option>
                            <option value="Manitoba">Canada/Manitoba</option>
                            <option value="New Brunswick">Canada/New Brunswick</option>
                            <option value="Newfoundland">Canada/Newfoundland</option>
                            <option value="Northwest Territories">Canada/Northwest
                                Territories</option>
                            <option value="Nova Scotia">Canada/Nova Scotia</option>
                            <option value="Ontario">Canada/Ontario</option>
                            <option value="Prince Edward Island">Canada/Prince
                                Edward Island</option>
                            <option value="Quebec">Canada/Quebec</option>
                            <option value="Saskatchewan">Canada/Saskatchewan</option>
                            <option value="Yukon">Canada/Yukon</option>
                            <option value="Canada">Canada</option>

                        </select>
                    </div>
                    
                    <div class="form-group col-lg-10">                
                        <select id="busType" name="Job_Category__c" class="form-control"
                            ng-model="$parent.Job_Category__c" ng-required="true"
                            tooltip="{{(form.Job_Category__c.$dirty && form.Job_Category__c.$error.required) ? 'Select a valid job role' : ''}}">
                            <option value="">Select Business Type</option>
                            <option value="ESCO">ESCO</option>
                            <option value="Energy Consultant Services">Energy Consultant Services</option>
                            <option value="Design/Build (EPC)">Design/Build (EPC)</option>
                            <option value="Engineering Firm">Engineering Firm</option>
                            <option value="Distributor/Manufacture/Vendor/Agent">Distributor/Manufacture/Vendor/Agent</option>
                            <option value="Retail Energy/Utility">Retail Energy/Utility</option>
                            <option value="Property Management/Real-Estate">Property Management/Real-Estate</option>
                            <option value="Commercial/Industrial/Office Bldg/Retail">Commercial/Industrial/Office Bldg/Retail</option>
                            <option value="Public/State/Gov't">Public/State/Gov't</option>
                            <option value="Financial Services/Lending/Broker">Financial Services/Lending/Broker</option>
                            <option value="Other">Other</option>
                        </select>
                     </div>
                     
                     <div class="form-group col-lg-10">          
                        <hr>
                        <p class="ne-11" style="color:#fff;">Do you sell energy projects and/or services to building owners?</p>
                        <ul id="sellQ" class="list-inline">
                            <li><input type="radio"
                                onChange="showSellSideQuestions();_gaq.push(['_trackEvent', 'MarketingForms', 'SellSideOptIn', 'Pro']);"
                                name="Sell_Side_Opt_In__c" value="Pro"
                                ng-model="$parent.Sell_Side_Opt_In__c"> <span
                                class="ne-11" style="color:#fff;"> Yes</span></li>
                            <li><input type="radio" 
                                onChange="showSellSideQuestions();_gaq.push(['_trackEvent', 'MarketingForms', 'SellSideOptIn', 'Pro']);"
                                name="Sell_Side_Opt_In__c" value="Pro"
                                ng-model="$parent.Sell_Side_Opt_In__c">
                                <span class="ne-11" style="color:#fff;"> No   </span></li>
                        </ul>
                    </div>
                     
                     <div class="form-group col-lg-10">     
                        <select id="companySize" name="Company_Size__c" class="form-control"
                            ng-model="$parent.Company_Size__c" ng-required="true"
                            tooltip="{{(form.Company_Size__c.$dirty && form.Company_Size__c.$error.required) ? 'Select a valid company size' : ''}}">
                            <option value="">Select Number of Employees in Company</option>
                            <option value="1,001+">1,001+</option>
                             <option value="501-1,000">501-1,000</option>
                             <option value="251-500">251-500</option>
                             <option value="101-250">101-250</option>
                             <option value="51-100">51-100</option>
                             <option value="20-50">20-50</option>
                            <option value="11-19">11-19</option>
                            <option value="1-10">1-10</option>
                            
                        </select>
                     </div>
                     
                     <div>        
                        <select id="companyRevenue" name="Annual_Revenue_Picklist__c" class="form-control"
                            ng-model="$parent.Annual_Revenue_Picklist__c" ng-required="true"
                            tooltip="{{(form.Annual_Revenue_Picklist__c.$dirty && form.Annual_Revenue_Picklist__c.$error.required) ? 'Select a valid annual revenue' : ''}}">
                                <option value="">Select Annual Company Revenue</option>
                                <option value="$10M+">$10M+</option>
                                 <option value="$5M-$9.9M">$5M-$10M</option>
                                 <option value="$2.5M-$4.9M">$2.5M-$4.9M</option>
                                <option value="&lt; $2.5M">&lt; $2.5M</option>
                        </select>
                     </div>

                     <% if(!customQuestion.equals("")){   %>
                     <div class="form-group col-lg-10">
                        <hr>
                        <p class="ne-11" style="color:#fff;"><%=customQuestion%></p>
                        <ul id="customQuestion" class="list-inline">
                            <li><input type="radio" name="customQ" value="yes" name="customQ">
                                
                                <span
                                class="ne-11" style="color:#fff;"> Yes</span></li>
                            <li><input type="radio" name="customQ" value="no" name="customQ">
                                
                                <span class="ne-11" style="color:#fff;"> No   </span></li>
                        </ul>
                     </div>
                     <% } %>
                                    
                                
        <div>
           <button type="input" class="btn btn-primary">Register for Webinar</button>
        </div>
        <div>
            <br>
            <a href="/webinarList.jsp"> << Back to webinar list </a>
            <br>
            <br>
            <td>
        </div>        
        </fieldset>
      </form>
    </div>
          
    
</div>


<div class="col-md-6">
    <div class="well">
    <h3 class="greeting text-primary">About this webinar : </h3>
    <p style="color:#444444"> <br>
        <%=webinar.getDescription()%> 
        <br> 
    </p>
    </div>
    <center>
    <img src="noesisEmail.jpg"></img>
    </center>
</div>
</div> <!-- end container -->

<script>
    $(document).ready(function() {
        $("#companySize").hide();
        $("#companyRevenue").hide();
        
     // validate signup form on keyup and submits
		var validator = $("#regForm").validate({
			rules: {
				first_name: "required",
				last_name: "required",
				email: "required",
				Phone: "required",
				Company: "required",
				Company_Headquarter_State__c: "required",
				Job_Category__c: "required",
                Sell_Side_Opt_In__c: "required",
                customQ: "required",
                Company_Size__c: "required",
                Annual_Revenue_Picklist__c: "required"
			},

			
			errorPlacement: function(error, element) {
				if (element.is(":radio"))
					error.appendTo(element.parent().next());
				else{
                    error.appendTo(element.parent());
					element.parent().addClass("has-error");
                }
			},

			
		});
    });

    function showSellSideQuestions() {
        $("#companySize").show();
        $("#companyRevenue").show();

    }

    function hideSellSideQuestions() {
        $("#companySize").hide();
        $("#companyRevenue").hide();
    }
</script>

<script>var _gaq = _gaq || [];_gaq.push(["_setAccount", "UA-25040971-4"]);_gaq.push(["_trackPageview"]);(function() {var ga = document.createElement("script");ga.type = "text/javascript";ga.async = true;ga.src = ("https:" == document.location.protocol ? "https://ssl" : "http://www") + ".google-analytics.com/ga.js";var s = document.getElementsByTagName("script")[0];s.parentNode.insertBefore(ga, s);})();</script>



</body>
</html>