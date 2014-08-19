<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.noesis.webinar.*" %>

<html>
<head>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="/jquery.validate.min.js"></script>
    
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="http://noesisimg.s3.amazonaws.com/HTML/ne-bootstrap/noesis-theme.css">

    
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

	DateAndTime when = new DateAndTime(webinar.getTimes().get(0), webinar.getTimeZone());
%>

<body style="padding:15px;">

<header>
	<div id="reg">
        <h1 class="text-info"><%=webinar.getSubject()%></h1>
    </div>
    <h4 style="color:#fff;">    
        <%=when.getDay()%> <%=when.getDate()%> <%=when.getStartTime()%> - <%=when.getEndTime()%> <%=webinar.getTimeZone()%>
    </h4>
    <hr>
</header>

<table>
<tr>
<td style="width:460px; padding-right:50px; vertical-align:top;"> 
<div>
    <form id="regForm" name="webinarRegistration" "${pageContext.request.contextPath}/WebinarWebAppServlet" action=<%=action%> method="POST" style="background-color: #f1f1ce; border-color: #adadad; border-radius: 7px; padding:10px;">
    <table>
    		<tr>
                <td colspan="2">
                    <h4><span class="label label-info">Register for this webinar : </span></h4><span style="font-size:12px; color:#777;"> (all fields required)</span>
                    <br>
                </td>
            </tr>

    	  	<tr>
                <td style="color:#777;">First Name * </td>
                <td>
    	        <input id="firstName" type="text" 
    	                name="first_name" class="form-control" ne-hint="First Name" 
    	                tooltip="{{(form.First_Name_Casual__c.$dirty && (form.First_Name_Casual__c.$error.required || form.First_Name_Casual__c.$error.maxlength)) ? 'Your first name is required and must be less than 255 characters' : ''}}"         
    	                ng-required="true" ng-maxlength="255" ng-model="$parent.First_Name_Casual__c">
    		    </td>
            </tr>

    		<tr><td style="color:#777;">
    	        Last Name *
    	    </td><td>
    	        <input id="lastName" type="text" 
    	                name="last_name" class="form-control" ne-hint="Last Name"
    	                tooltip="{{(form.LastName.$dirty && (form.LastName.$error.required || form.LastName.$error.maxlength)) ? 'Your last name is required and must be less than 255 characters' : ''}}"         
    	                ng-required="true" ng-maxlength="255" ng-model="$parent.LastName">
    		</td></tr>

    		<tr><td style="color:#777;">
    	        Email *
    	    </td><td>
    	        <input id="email" type="email" 
    	                name="email" class="form-control" ne-hint="Email"
    	                tooltip="{{(form.Email.$dirty && (form.Email.$error.required || form.Email.$error.maxlength || form.Email.$error.email)) ? 'A valid email address is required' : ''}}"         
    	                ng-required="true" ng-maxlength="255" ng-model="$parent.Email">
    		</td></tr>
    	
    		<tr><td style="color:#777;">
                            Phone *
            </td><td>
                            <input id="phone" type="tel" 
                                    name="Phone" class="form-control" ne-hint="Phone (xxx-xxx-xxxx)"
                                    tooltip="{{(form.Phone.$dirty && (form.Phone.$error.required || form.Phone.$error.maxlength || form.Phone.$error.minlength || form.Phone.$error.tel)) ? 'A valid phone number is required' : ''}}"         
                                    ng-required="true" ng-minlength="10" ng-maxlength="16" ng-model="$parent.Phone">
            </td></tr>
            
            <tr><td style="color:#777;">
                            Company Name *
           	</td><td>
                            <input id="companyName" type="text" 
                                    name="Company" class="form-control" ne-hint="Company"
                                    tooltip="{{(form.Company.$dirty && (form.Company.$error.required || form.Company.$error.maxlength)) ? 'Your company name is required and must be less than 255 characters' : ''}}"         
                                    ng-required="true" ng-maxlength="255" ng-model="$parent.Company">
             </td></tr>
             
             <tr><td style="color:#777;">
             				Company Location *
             </td><td>
                    
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
                                        <option value="Afghanistan">Afghanistan</option>
                                        <option value="Albania">Albania</option>
                                        <option value="Algeria">Algeria</option>
                                        <option value="American Samoa">American Samoa</option>
                                        <option value="Andorra">Andorra</option>
                                        <option value="Angola">Angola</option>
                                        <option value="Anguilla">Anguilla</option>
                                        <option value="Antarctica">Antarctica</option>
                                        <option value="Antigua and Barbuda">Antigua and
                                            Barbuda</option>
                                        <option value="Argentina">Argentina</option>
                                        <option value="Armenia">Armenia</option>
                                        <option value="Aruba">Aruba</option>
                                        <option value="Australia">Australia</option>
                                        <option value="Austria">Austria</option>
                                        <option value="Azerbaijan">Azerbaijan</option>
                                        <option value="Bahamas">Bahamas</option>
                                        <option value="Bahrain">Bahrain</option>
                                        <option value="Bangladesh">Bangladesh</option>
                                        <option value="Barbados">Barbados</option>
                                        <option value="Belarus">Belarus</option>
                                        <option value="Belgium">Belgium</option>
                                        <option value="Belize">Belize</option>
                                        <option value="Benin">Benin</option>
                                        <option value="Bermuda">Bermuda</option>
                                        <option value="Bhutan">Bhutan</option>
                                        <option value="Bolivia">Bolivia</option>
                                        <option value="Bosnia and Herzegovina">Bosnia and
                                            Herzegovina</option>
                                        <option value="Botswana">Botswana</option>
                                        <option value="Bouvet Island">Bouvet Island</option>
                                        <option value="Brazil">Brazil</option>
                                        <option value="British Indian Ocean Territory">British
                                            Indian Ocean Territory</option>
                                        <option value="Brunei Darussalam">Brunei Darussalam</option>
                                        <option value="Bulgaria">Bulgaria</option>
                                        <option value="Burkina Faso">Burkina Faso</option>
                                        <option value="Burundi">Burundi</option>
                                        <option value="Cambodia">Cambodia</option>
                                        <option value="Cameroon">Cameroon</option>
                                        <option value="Canada">Canada</option>
                                        <option value="Cape Verde">Cape Verde</option>
                                        <option value="Cayman Islands">Cayman Islands</option>
                                        <option value="Central African Republic">Central
                                            African Republic</option>
                                        <option value="Chad">Chad</option>
                                        <option value="Chile">Chile</option>
                                        <option value="China">China</option>
                                        <option value="Christmas Island">Christmas Island</option>
                                        <option value="Cocos (Keeling) Islands">Cocos
                                            (Keeling) Islands</option>
                                        <option value="Colombia">Colombia</option>
                                        <option value="Comoros">Comoros</option>
                                        <option value="Congo">Congo</option>
                                        <option value="Cook Islands">Cook Islands</option>
                                        <option value="Costa Rica">Costa Rica</option>
                                        <option value="Cote D'ivoire">Cote D'ivoire</option>
                                        <option value="Croatia">Croatia</option>
                                        <option value="Cuba">Cuba</option>
                                        <option value="Cyprus">Cyprus</option>
                                        <option value="Czech Republic">Czech Republic</option>
                                        <option value="Denmark">Denmark</option>
                                        <option value="Djibouti">Djibouti</option>
                                        <option value="Dominica">Dominica</option>
                                        <option value="Dominican Republic">Dominican Republic</option>
                                        <option value="Ecuador">Ecuador</option>
                                        <option value="Egypt">Egypt</option>
                                        <option value="El Salvador">El Salvador</option>
                                        <option value="Equatorial Guinea">Equatorial Guinea</option>
                                        <option value="Eritrea">Eritrea</option>
                                        <option value="Estonia">Estonia</option>
                                        <option value="Ethiopia">Ethiopia</option>
                                        <option value="Falkland Islands (Malvinas)">Falkland
                                            Islands (Malvinas)</option>
                                        <option value="Faroe Islands">Faroe Islands</option>
                                        <option value="Fiji">Fiji</option>
                                        <option value="Finland">Finland</option>
                                        <option value="France">France</option>
                                        <option value="French Guiana">French Guiana</option>
                                        <option value="French Polynesia">French Polynesia</option>
                                        <option value="French Southern Territories">French
                                            Southern Territories</option>
                                        <option value="Gabon">Gabon</option>
                                        <option value="Gambia">Gambia</option>
                                        <option value="Georgia">Georgia</option>
                                        <option value="Germany">Germany</option>
                                        <option value="Ghana">Ghana</option>
                                        <option value="Gibraltar">Gibraltar</option>
                                        <option value="Greece">Greece</option>
                                        <option value="Greenland">Greenland</option>
                                        <option value="Grenada">Grenada</option>
                                        <option value="Guadeloupe">Guadeloupe</option>
                                        <option value="Guam">Guam</option>
                                        <option value="Guatemala">Guatemala</option>
                                        <option value="Guinea">Guinea</option>
                                        <option value="Guinea-bissau">Guinea-bissau</option>
                                        <option value="Guyana">Guyana</option>
                                        <option value="Haiti">Haiti</option>
                                        <option value="Honduras">Honduras</option>
                                        <option value="Hong Kong">Hong Kong</option>
                                        <option value="Hungary">Hungary</option>
                                        <option value="Iceland">Iceland</option>
                                        <option value="India">India</option>
                                        <option value="Indonesia">Indonesia</option>
                                        <option value="Iran, Islamic Republic of">Iran,
                                            Islamic Republic of</option>
                                        <option value="Iraq">Iraq</option>
                                        <option value="Ireland">Ireland</option>
                                        <option value="Israel">Israel</option>
                                        <option value="Italy">Italy</option>
                                        <option value="Jamaica">Jamaica</option>
                                        <option value="Japan">Japan</option>
                                        <option value="Jordan">Jordan</option>
                                        <option value="Kazakhstan">Kazakhstan</option>
                                        <option value="Kenya">Kenya</option>
                                        <option value="Kiribati">Kiribati</option>
                                        <option value="Korea, Democratic People's Republic of">Korea,
                                            Democratic People's Republic of</option>
                                        <option value="Korea, Republic of">Korea, Republic of</option>
                                        <option value="Kuwait">Kuwait</option>
                                        <option value="Kyrgyzstan">Kyrgyzstan</option>
                                        <option value="Lao People's Democratic Republic">Lao
                                            People's Democratic Republic</option>
                                        <option value="Latvia">Latvia</option>
                                        <option value="Lebanon">Lebanon</option>
                                        <option value="Lesotho">Lesotho</option>
                                        <option value="Liberia">Liberia</option>
                                        <option value="Libyan Arab Jamahiriya">Libyan Arab
                                            Jamahiriya</option>
                                        <option value="Liechtenstein">Liechtenstein</option>
                                        <option value="Lithuania">Lithuania</option>
                                        <option value="Luxembourg">Luxembourg</option>
                                        <option value="Macao">Macao</option>
                                        <option value="Macedonia, The Former Yugoslav Republic of">Macedonia,
                                            The Former Yugoslav Republic of</option>
                                        <option value="Madagascar">Madagascar</option>
                                        <option value="Malawi">Malawi</option>
                                        <option value="Malaysia">Malaysia</option>
                                        <option value="Maldives">Maldives</option>
                                        <option value="Mali">Mali</option>
                                        <option value="Malta">Malta</option>
                                        <option value="Marshall Islands">Marshall Islands</option>
                                        <option value="Martinique">Martinique</option>
                                        <option value="Mauritania">Mauritania</option>
                                        <option value="Mauritius">Mauritius</option>
                                        <option value="Mayotte">Mayotte</option>
                                        <option value="Mexico">Mexico</option>
                                        <option value="Micronesia, Federated States of">Micronesia,
                                            Federated States of</option>
                                        <option value="Moldova, Republic of">Moldova, Republic
                                            of</option>
                                        <option value="Monaco">Monaco</option>
                                        <option value="Mongolia">Mongolia</option>
                                        <option value="Montserrat">Montserrat</option>
                                        <option value="Morocco">Morocco</option>
                                        <option value="Mozambique">Mozambique</option>
                                        <option value="Myanmar">Myanmar</option>
                                        <option value="Namibia">Namibia</option>
                                        <option value="Nauru">Nauru</option>
                                        <option value="Nepal">Nepal</option>
                                        <option value="Netherlands">Netherlands</option>
                                        <option value="Netherlands Antilles">Netherlands
                                            Antilles</option>
                                        <option value="New Caledonia">New Caledonia</option>
                                        <option value="New Zealand">New Zealand</option>
                                        <option value="Nicaragua">Nicaragua</option>
                                        <option value="Niger">Niger</option>
                                        <option value="Nigeria">Nigeria</option>
                                        <option value="Niue">Niue</option>
                                        <option value="Norfolk Island">Norfolk Island</option>
                                        <option value="Northern Mariana Islands">Northern
                                            Mariana Islands</option>
                                        <option value="Norway">Norway</option>
                                        <option value="Oman">Oman</option>
                                        <option value="Pakistan">Pakistan</option>
                                        <option value="Palau">Palau</option>
                                        <option value="Palestinian Territory, Occupied">Palestinian
                                            Territory, Occupied</option>
                                        <option value="Panama">Panama</option>
                                        <option value="Papua New Guinea">Papua New Guinea</option>
                                        <option value="Paraguay">Paraguay</option>
                                        <option value="Peru">Peru</option>
                                        <option value="Philippines">Philippines</option>
                                        <option value="Pitcairn">Pitcairn</option>
                                        <option value="Poland">Poland</option>
                                        <option value="Portugal">Portugal</option>
                                        <option value="Puerto Rico">Puerto Rico</option>
                                        <option value="Qatar">Qatar</option>
                                        <option value="Reunion">Reunion</option>
                                        <option value="Romania">Romania</option>
                                        <option value="Russian Federation">Russian Federation</option>
                                        <option value="Rwanda">Rwanda</option>
                                        <option value="Saint Helena">Saint Helena</option>
                                        <option value="Saint Kitts and Nevis">Saint Kitts and
                                            Nevis</option>
                                        <option value="Saint Lucia">Saint Lucia</option>
                                        <option value="Saint Pierre and Miquelon">Saint Pierre
                                            and Miquelon</option>
                                        <option value="Saint Vincent and The Grenadines">Saint
                                            Vincent and The Grenadines</option>
                                        <option value="Samoa">Samoa</option>
                                        <option value="San Marino">San Marino</option>
                                        <option value="Sao Tome and Principe">Sao Tome and
                                            Principe</option>
                                        <option value="Saudi Arabia">Saudi Arabia</option>
                                        <option value="Senegal">Senegal</option>
                                        <option value="Serbia and Montenegro">Serbia and
                                            Montenegro</option>
                                        <option value="Seychelles">Seychelles</option>
                                        <option value="Sierra Leone">Sierra Leone</option>
                                        <option value="Singapore">Singapore</option>
                                        <option value="Slovakia">Slovakia</option>
                                        <option value="Slovenia">Slovenia</option>
                                        <option value="Solomon Islands">Solomon Islands</option>
                                        <option value="Somalia">Somalia</option>
                                        <option value="South Africa">South Africa</option>
                                        <option value="South Georgia and The South Sandwich Islands">South
                                            Georgia and The South Sandwich Islands</option>
                                        <option value="Spain">Spain</option>
                                        <option value="Sri Lanka">Sri Lanka</option>
                                        <option value="Sudan">Sudan</option>
                                        <option value="Suriname">Suriname</option>
                                        <option value="Svalbard and Jan Mayen">Svalbard and
                                            Jan Mayen</option>
                                        <option value="Swaziland">Swaziland</option>
                                        <option value="Sweden">Sweden</option>
                                        <option value="Switzerland">Switzerland</option>
                                        <option value="Syrian Arab Republic">Syrian Arab
                                            Republic</option>
                                        <option value="Taiwan, Province of China">Taiwan,
                                            Province of China</option>
                                        <option value="Tajikistan">Tajikistan</option>
                                        <option value="Tanzania, United Republic of">Tanzania,
                                            United Republic of</option>
                                        <option value="Thailand">Thailand</option>
                                        <option value="Timor-leste">Timor-leste</option>
                                        <option value="Togo">Togo</option>
                                        <option value="Tokelau">Tokelau</option>
                                        <option value="Tonga">Tonga</option>
                                        <option value="Trinidad and Tobago">Trinidad and
                                            Tobago</option>
                                        <option value="Tunisia">Tunisia</option>
                                        <option value="Turkey">Turkey</option>
                                        <option value="Turkmenistan">Turkmenistan</option>
                                        <option value="Turks and Caicos Islands">Turks and
                                            Caicos Islands</option>
                                        <option value="Tuvalu">Tuvalu</option>
                                        <option value="Uganda">Uganda</option>
                                        <option value="Ukraine">Ukraine</option>
                                        <option value="United Arab Emirates">United Arab
                                            Emirates</option>
                                        <option value="United Kingdom">United Kingdom</option>
                                        <option value="United States Minor Outlying Islands">United
                                            States Minor Outlying Islands</option>
                                        <option value="Uruguay">Uruguay</option>
                                        <option value="Uzbekistan">Uzbekistan</option>
                                        <option value="Vanuatu">Vanuatu</option>
                                        <option value="Venezuela">Venezuela</option>
                                        <option value="Viet Nam">Viet Nam</option>
                                        <option value="Virgin Islands, British">Virgin
                                            Islands, British</option>
                                        <option value="Virgin Islands, U.S.">Virgin Islands,
                                            U.S.</option>
                                        <option value="Wallis and Futuna">Wallis and Futuna</option>
                                        <option value="Western Sahara">Western Sahara</option>
                                        <option value="Yemen">Yemen</option>
                                        <option value="Zambia">Zambia</option>
                                        <option value="Zimbabwe">Zimbabwe</option>

                                    </select>
                    </td></tr>
                    
                    <tr><td style="color:#777;">
                    				Business Type *
                    </td><td>
                                
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
                     </td></tr>
                     
                     <tr><td colspan="2">
                               		<hr>
                                    <p class="ne-11" style="color:#777;">Do you sell energy projects and/or services to building owners?</p>
                                    <ul id="sellQ" class="list-inline">
                                        <li><input type="radio"
                                            onChange="showSellSideQuestions();_gaq.push(['_trackEvent', 'MarketingForms', 'SellSideOptIn', 'Pro']);"
                                            name="Sell_Side_Opt_In__c" value="Pro"
                                            ng-model="$parent.Sell_Side_Opt_In__c"> <span
                                            class="ne-11" style="color:#777;"> Yes</span</li>
                                        <li><input type="radio" onChange="hideSellSideQuestions();"
                                            name="Sell_Side_Opt_In__c"
                                            value="NA" ng-model="$parent.Sell_Side_Opt_In__c">
                                            <span class="ne-11" style="color:#777;"> No</span></li>
                                    </ul>
                     </td></tr>
                     
                     <tr><td colspan="2">
                                
                                    <select id="companySize" name="Company_Size__c" class="form-control"
                                        ng-model="$parent.Company_Size__c" ng-required="false"
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
                     </td></tr>
                     
                     <tr><td colspan="2">
                                
                                    <select id="companyRevenue" name="Annual_Revenue_Picklist__c" class="form-control"
                                        ng-model="$parent.Annual_Revenue_Picklist__c" ng-required="false"
                                        tooltip="{{(form.Annual_Revenue_Picklist__c.$dirty && form.Annual_Revenue_Picklist__c.$error.required) ? 'Select a valid annual revenue' : ''}}">
                                            <option value="">Select Annual Company Revenue</option>
                                            <option value="$10M+">$10M+</option>
                                             <option value="$5M-$9.9M">$5M-$10M</option>
                                             <option value="$2.5M-$4.9M">$2.5M-$4.9M</option>
                                            <option value="&lt; $2.5M">&lt; $2.5M</option>
                                            </select>
                                           
                                            
                                    </select>
                     </td></tr>
                                    
                                
    	<tr><td colspan="2">
    	   <hr>
           <button type="input" class="btn btn-primary">Register</button>
    	</td></tr>
        <tr> <td colspan="2">
            <br>
            <a href="/webinarList.jsp"> << Back to webinar list </a>
            <br>
            <br>
            <td>
        </tr>    
    </table>
    </form>
</div>
</td>

<td colspan="2" style="vertical-align:top; horizontal-align:left">
    <h4><span class="label label-info">About this webinar : </span></h4>
    <p style="color:#fff;"> <br>
        <%=webinar.getDescription()%> 
        <br> 
    </p>
        
<td>
</tr>
</table>

<script>
    $(document).ready(function() {
        $("#companySize").hide();
        $("#companyRevenue").hide();
        
     // validate signup form on keyup and submits
		var validator = $("#regForm").validate({
			rules: {
				
				first_name: "required"
				
			},
			messages: {
				
				first_name: "Enter your firstname"
			},
			// the errorPlacement has to take the table layout into account
			errorPlacement: function(error, element) {
				if (element.is(":radio"))
					error.appendTo(element.parent().next().next());
				else if (element.is(":checkbox"))
					error.appendTo(element.next());
				else
					error.appendTo(element.parent().next());
			},
			// specifying a submitHandler prevents the default submit, good for the demo
			/* submitHandler: function() {
				alert("submitted!");
			}, */
			// set this class to error-labels to indicate valid fields
			success: function(label) {
				// set &nbsp; as text for IE
				label.html("&nbsp;").addClass("checked");
			},
			highlight: function(element, errorClass) {
				$(element).parent().next().find("." + errorClass).removeClass("checked");
			}
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