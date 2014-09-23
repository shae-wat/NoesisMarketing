package com.noesis.marketo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.noesis.webinar.WebinarAuth;
import com.noesis.webinar.WebinarUser;

public class MarketoConnector {
	
	Gson gson; 
	MarketoAuth auth;
	
	String restUrl = "https://716-WKH-023.mktorest.com/rest/";
	String identityUrl = "https://716-WKH-023.mktorest.com/identity/";
	
	String accessToken = "";

	public static void main (String args[])
	{
		try {
			MarketoConnector mc = new MarketoConnector();
			WebinarLead wl = mc.populateTestLead();
			mc.addLeadToWebinarAttendence(mc.createListOfOne(wl));
		} catch (Exception e) { e.printStackTrace(); }
		
	}
	
	public MarketoConnector ()
	{
		try {
			gson = new Gson();
			URL url = new URL(identityUrl + "oauth/token?grant_type=client_credentials"
					+ "&client_id=9267e87c-70c1-49da-8659-a310c2954cfc&client_secret=GKNcRCRJoQZymmmRqoKImVCIxGCpqo8x");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line ="";
		    String rline = "";

		    while ((rline = reader.readLine()) != null) {
		        line = line + rline;
		    }
		    reader.close();
		    
		    System.out.println(line);
		    auth = gson.fromJson(line, MarketoAuth.class);
		    System.out.println(auth.getAccess_token());
		    this.accessToken = auth.getAccess_token();
		    
		 
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void addLead (String fname, String lname, String email, 
			String phone, String company, String location,
			String jobType, String companySize, String annualRevenue,
			String customQuestion, String webinarName)
	{
		WebinarLead wl = new WebinarLead();
		wl.setFirst_Name_Casual__c(fname);
		wl.setLastName(lname);
		wl.setEmail(email);
		wl.setPhone(phone);
		wl.setCompany(company);
		wl.setCompany_Headquarter_State__c(location);
		wl.setJob_Category__c(jobType);
		wl.setCompany_Size__c(companySize);
		wl.setAnnual_Revenue_Picklist__c(annualRevenue);
		wl.setWebinar_Custom_Question_Answer__c(customQuestion);
		wl.setWebinar_Name__c(webinarName);
		
		WebinarLeadList wll = createListOfOne (wl);
		try {
			addLeadToWebinarAttendence(wll);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void addLeadToWebinarAttendence (WebinarLeadList leads) throws IOException {
		
		String leadJsonStr = gson.toJson(leads, WebinarLeadList.class);
		System.out.println(leadJsonStr);
		
		URL url = new URL(this.restUrl + "v1/leads.json?access_token=" + this.accessToken);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(leadJsonStr);
        writer.close();
        
        System.out.println("Response Code " + connection.getResponseCode());
        String regReponse = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((regReponse = in.readLine()) != null)
		{
			System.out.println(regReponse);
		}
		in.close();
		
	}
	
	private WebinarLeadList createListOfOne (WebinarLead wl)
	{
		WebinarLeadList wll = new WebinarLeadList();
		wll.addLead(wl);
		return wll;
	}
	
	private WebinarLead populateTestLead ()
	{
		WebinarLead wl = new WebinarLead();
		wl.setFirst_Name_Casual__c("Scott");
		wl.setLastName("Carter");
		wl.setEmail("scarter2222@noesis.com");
		wl.setPhone("5127737187");
		wl.setCompany("Noesis");
		wl.setCompany_Headquarter_State__c("Texas");
		wl.setJob_Category__c("Energy Consultant Services");
		wl.setCompany_Size__c("20-50");
		wl.setAnnual_Revenue_Picklist__c("$5M-$9.9M");
		
		return wl;
	}
	
	private class WebinarLeadList {
		
		String action = "createOrUpdate";
		List<WebinarLead> input = new ArrayList<WebinarLead>();
		
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		
		public List<WebinarLead> getInput() {
			return input;
		}
		public void setInput(List<WebinarLead> input) {
			this.input = input;
		}
		public void addLead (WebinarLead lead)
		{
			input.add(lead);
		}
		
	}
	
	public class WebinarLead {
		String First_Name_Casual__c = "";
		String LastName = "";
		String Email = "";
		String Phone = "";
		String Company = "";
		String Company_Headquarter_State__c = "";
		String Job_Category__c = "";
		String Company_Size__c = "";
		String Annual_Revenue_Picklist__c = "";
		String Webinar_Name__c = "";
		String Webinar_Custom_Question_Answer__c = "";
		
		
		public String getFirst_Name_Casual__c() {
			return First_Name_Casual__c;
		}
		public void setFirst_Name_Casual__c(String first_Name_Casual__c) {
			First_Name_Casual__c = first_Name_Casual__c;
		}
		public String getLastName() {
			return LastName;
		}
		public void setLastName(String lastName) {
			LastName = lastName;
		}
		public String getEmail() {
			return Email;
		}
		public void setEmail(String email) {
			Email = email;
		}
		public String getPhone() {
			return Phone;
		}
		public void setPhone(String phone) {
			Phone = phone;
		}
		public String getCompany() {
			return Company;
		}
		public void setCompany(String company) {
			Company = company;
		}
		public String getCompany_Headquarter_State__c() {
			return Company_Headquarter_State__c;
		}
		public void setCompany_Headquarter_State__c(String company_Headquarter_State__c) {
			Company_Headquarter_State__c = company_Headquarter_State__c;
		}
		public String getJob_Category__c() {
			return Job_Category__c;
		}
		public void setJob_Category__c(String job_Category__c) {
			Job_Category__c = job_Category__c;
		}
		public String getCompany_Size__c() {
			return Company_Size__c;
		}
		public void setCompany_Size__c(String company_Size__c) {
			Company_Size__c = company_Size__c;
		}
		public String getAnnual_Revenue_Picklist__c() {
			return Annual_Revenue_Picklist__c;
		}
		public void setAnnual_Revenue_Picklist__c(String annual_Revenue_Picklist__c) {
			Annual_Revenue_Picklist__c = annual_Revenue_Picklist__c;
		}
		public String getWebinar_Name__c() {
			return Webinar_Name__c;
		}
		public void setWebinar_Name__c(String webinar_Name__c) {
			Webinar_Name__c = webinar_Name__c;
		}
		public String getWebinar_Custom_Question_Answer__c() {
			return Webinar_Custom_Question_Answer__c;
		}
		public void setWebinar_Custom_Question_Answer__c(
				String webinar_Custom_Question_Answer__c) {
			Webinar_Custom_Question_Answer__c = webinar_Custom_Question_Answer__c;
		}
		
		
		
	}
	
}
