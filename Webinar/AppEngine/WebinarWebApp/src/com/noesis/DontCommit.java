//package com.noesis;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.noesis.webinar.*;
//
//public class DontCommit {
//	
//	public static void main (String args[])
//	{
//		DontCommit dc = new DontCommit();
//		try {
//			dc.doStuff();
//		} catch (Exception e) { e.printStackTrace(); }
//	}
//	
//	public void doStuff () throws IOException
//	{
//		WebinarConnector wc = new WebinarConnector();
//		
//		Set<String> attAddresses = new HashSet<String>();
//		Set<String> regAddresses = new HashSet<String>();
//		
//		List<String> attAll = new ArrayList<String>();
//		List<String> regAll = new ArrayList<String>();
//		
//		String historicalCall = "https://api.citrixonline.com/G2W/rest/organizers/" + wc.wa.getOrganizer_key() + "/historicalWebinars?fromTime=2012-09-01T18:00:00Z&toTime=2014-08-31T18:00:00Z";
//		URL url = new URL(historicalCall);
//		
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//		connection.setRequestMethod("GET");
//		connection.setRequestProperty("Authorization", "OAuth oauth_token="+ wc.wa.getAccess_token());
//		connection.connect();
//		
//		Gson gson = new Gson();
//		String line = "";
//		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//		
//		String rline = "";
//		while((line = in.readLine()) != null)
//		{
//			System.out.println("\nGET upcoming webinars = "+line);
//			rline = rline + line;
//		}
//		Webinars[] webinarsArr = gson.fromJson(rline, Webinars[].class);
//		System.out.println("Found " + webinarsArr.length);
//		in.close();
//		
//		int count = 0;
//		for (int i=0; i < webinarsArr.length; i++)
//		{
//			count++;
//			System.out.println(count + " : " + webinarsArr[i].getSubject());
//			if (!webinarsArr[i].getSubject().contains("TEST"))
//			{
//				String attendeeCall = "https://api.citrixonline.com/G2W/rest/organizers/" + wc.wa.getOrganizer_key() + "/webinars/" + webinarsArr[i].getWebinarKey() + "/attendees";
//				String registrantCall = "https://api.citrixonline.com/G2W/rest/organizers/" + wc.wa.getOrganizer_key() + "/webinars/" + webinarsArr[i].getWebinarKey() + "/registrants";
//				
//				URL atturl = new URL(attendeeCall);
//				URL regurl = new URL(registrantCall);
//				
//				HttpURLConnection attconnection = (HttpURLConnection) atturl.openConnection();
//				attconnection.setRequestMethod("GET");
//				attconnection.setRequestProperty("Authorization", "OAuth oauth_token="+ wc.wa.getAccess_token());
//				attconnection.connect();
//				
//				line = "";
//				in = new BufferedReader(new InputStreamReader(attconnection.getInputStream()));
//				
//				rline = "";
//				while((line = in.readLine()) != null)
//				{
//					System.out.println("\nGET upcoming webinars = "+line);
//					rline = rline + line;
//				}
//				Attendee[] AttendeeArr = gson.fromJson(rline, Attendee[].class);
//				if (AttendeeArr != null)
//				{
//					for (int j=0; j<AttendeeArr.length; j++)
//					{
//						attAddresses.add(AttendeeArr[j].getEmail());
//						attAll.add(AttendeeArr[j].getEmail());
//					}
//				}
//				
//				HttpURLConnection regconnection = (HttpURLConnection) regurl.openConnection();
//				regconnection.setRequestMethod("GET");
//				regconnection.setRequestProperty("Authorization", "OAuth oauth_token="+ wc.wa.getAccess_token());
//				regconnection.connect();
//				
//				line = "";
//				in = new BufferedReader(new InputStreamReader(regconnection.getInputStream()));
//				
//				rline = "";
//				while((line = in.readLine()) != null)
//				{
//					System.out.println("\nGET upcoming webinars = "+line);
//					rline = rline + line;
//				}
//				Registrant[] RegistrantArr = gson.fromJson(rline, Registrant[].class);
//				if (RegistrantArr != null){
//					for (int k=0; k<RegistrantArr.length; k++)
//					{
//						regAddresses.add(RegistrantArr[k].getEmail());
//						regAll.add(RegistrantArr[k].getEmail());
//					}
//				}
//			}
//		}
//		
//		System.out.println("Unique Reg " + regAddresses.size() + " Attendees " + attAddresses.size());
//		System.out.println("All Reg " + regAll.size() + " All Attendees " + attAll.size());
//	}
//	
//	public class Webinars {
//		String webinarKey = "";
//		String subject = "";
//		String description = "";
//		String organizerKey = "";
//		
//		
//		public String getWebinarKey() {
//			return webinarKey;
//		}
//		public void setWebinarKey(String webinarKey) {
//			this.webinarKey = webinarKey;
//		}
//		public String getSubject() {
//			return subject;
//		}
//		public void setSubject(String subject) {
//			this.subject = subject;
//		}
//		public String getDescription() {
//			return description;
//		}
//		public void setDescription(String description) {
//			this.description = description;
//		}
//		public String getOrganizerKey() {
//			return organizerKey;
//		}
//		public void setOrganizerKey(String organizerKey) {
//			this.organizerKey = organizerKey;
//		}
//		
//	}
//	
//	public class Attendee {
//		String firstName = "";
//		String lastName = "";
//		String email = "";
//		String attendanceTimeInSeconds = "";
//		
//		public String getFirstName() {
//			return firstName;
//		}
//		public void setFirstName(String firstName) {
//			this.firstName = firstName;
//		}
//		public String getLastName() {
//			return lastName;
//		}
//		public void setLastName(String lastName) {
//			this.lastName = lastName;
//		}
//		public String getEmail() {
//			return email;
//		}
//		public void setEmail(String email) {
//			this.email = email;
//		}
//		public String getAttendanceTimeInSeconds() {
//			return attendanceTimeInSeconds;
//		}
//		public void setAttendanceTimeInSeconds(String attendanceTimeInSeconds) {
//			this.attendanceTimeInSeconds = attendanceTimeInSeconds;
//		}
//		
//	}
//	
//	public class Registrant {
//		String firstName = "";
//		String lastName = "";
//		String email = "";
//		
//		
//		public String getFirstName() {
//			return firstName;
//		}
//		public void setFirstName(String firstName) {
//			this.firstName = firstName;
//		}
//		public String getLastName() {
//			return lastName;
//		}
//		public void setLastName(String lastName) {
//			this.lastName = lastName;
//		}
//		public String getEmail() {
//			return email;
//		}
//		public void setEmail(String email) {
//			this.email = email;
//		}
//		
//		
//	}
//	
//}
