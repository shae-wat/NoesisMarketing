package com.noesis.webinar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.UidGenerator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebinarConnector {

	WebinarAuth wa;
	Gson gson; 
	
	public WebinarConnector ()
	{
		try {
		    URL url = new URL("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line ="";
		    String rline = "";

		    while ((rline = reader.readLine()) != null) {
		        line = line + rline;
		    }
		    reader.close();
		    
		    gson = new Gson();
		    wa = gson.fromJson(line, WebinarAuth.class);
			System.out.println("\nGET auth data = " + line);
			System.out.println("access token = " + wa.getAccess_token());
			System.out.println("organizer key = " + wa.getOrganizer_key());

		} catch (MalformedURLException e) {
		    // ...
		} catch (IOException e) {
		    // ...
		}
	}
	
	public List<WebinarData> getUpcomingWebinars () throws IOException
	{
		List<WebinarData> upcomingWebinars = new ArrayList<WebinarData>();
		List<WebinarData> officialWebinars = new ArrayList<WebinarData>();
		
		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/upcomingWebinars");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
		connection.connect();
		
		String line = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = in.readLine()) != null)
		{
			System.out.println("\nGET upcoming webinars = "+line);
			upcomingWebinars = gson.fromJson(line, new TypeToken<List<WebinarData>>(){}.getType());
		}
		in.close();
		
		for (WebinarData webinar:upcomingWebinars){
			System.out.println(webinar.getSubject() + " : " + webinar.getWebinarKey());
			DateAndTime dateAndKey = null;
			
			dateAndKey = new DateAndTime(webinar.times.get(0), webinar.getTimeZone());  //shouldbe only ele at this point
			dateAndKey.setWebinarKey(webinar.getWebinarKey());
			System.out.println(dateAndKey.getStartTime());
			System.out.println(webinar.getTimeZone());
			dateAndKey.setTimeZone(webinar.getTimeZone());
			
			//Remove list duplicates
			for(WebinarData web:officialWebinars){
				if (webinar.getSubject().equals(web.getSubject())){
					System.out.println("Added " + webinar.getSubject() + " times to " + web.getSubject());
					web.datesAndKeys.add(dateAndKey);
					//web.times.add(webinar.times.get(0));
					webinar.setSubject("NEEDED");  //filters this webinar out after adding its times
				}
			}
			//Check for test webinars, which must include TEST in all caps in their title
			if(upcomingWebinars.contains(webinar) && !webinar.getSubject().contains("TEST") && !webinar.getSubject().contains("NEEDED")){
				webinar.datesAndKeys.add(dateAndKey);
				officialWebinars.add(webinar);
			}
		}
		Collections.sort(officialWebinars);
		
		connection.disconnect();
		return officialWebinars;

	}
	
	
	public WebinarData getWebinarInfo(String webinarId) throws IOException{
		
		WebinarData webinar = new WebinarData();
		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/" + webinarId);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
		connection.connect();
		
		String line = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = in.readLine()) != null)
		{
			System.out.println("\nGET webinar info = "+line);
			webinar = gson.fromJson(line, WebinarData.class);
		}
		in.close();
		
		return webinar;
	}
	
	public String getCustomQuestion(String webinarId) throws IOException{
		
		WebinarData webinar = new WebinarData();
		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/"  + webinarId + "/registrants/fields");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
		connection.connect();
		
		
		String line = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = in.readLine()) != null)
		{
			System.out.println("\nGET customQuestion = "+line);
			webinar = gson.fromJson(line, WebinarData.class);
		}
		in.close();
		
		
		
		try { return webinar.questions.get(0).getQuestion(); }
		catch (IndexOutOfBoundsException e) { return ""; }
	}
	
	
	public WebinarUser registerUser(String webinarId, WebinarUser user) throws IOException{

		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/"+ webinarId +"/registrants");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
		
			try {
				System.out.println("\nRegistering " + user.getFirstName() + " for " + webinarId);

				String registrationJSON = "{\"firstName\":\""+ user.getFirstName()+"\",\"lastName\":\""+ user.getLastName()+"\",\"email\":\""+ user.getEmail()+"\"}";
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            writer.write(registrationJSON);
	            writer.close();
	    
	            if (connection.getResponseCode() == 409){
	            	System.out.println(user.getFirstName() + " is already registered for webinar " + webinarId);
	            }
	            else {
	            	System.out.println("POST register user: Server returned HTTP code: " + connection.getResponseCode());
	            }
	            
	            String regReponse = "";
	    		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    		while((regReponse = in.readLine()) != null)
	    		{
	    			System.out.println(regReponse);
	    			gson = new Gson();
	    		    user = gson.fromJson(regReponse, WebinarUser.class);
	    		}
	    		in.close();
	    		
	    		System.out.println("joinUrl = " + user.getJoinUrl());
	    		System.out.println("registrantKey = " + user.getRegistrantKey());	

				
		} catch (MalformedURLException e) {
			e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
				
				
		return user;
	}
	
	public String getCalEvent(){
//		//Initilize values
//	  String calFile = "TestCalendar.ics";
//	  
//	  //start time
//	  java.util.Calendar startCal = java.util.Calendar.getInstance();
//	  startCal.set(2012, 04, 23, 20, 00);
//	  
//	  //end time
//	  java.util.Calendar endCal = java.util.Calendar.getInstance();
//	  endCal.set(2012, 04, 23, 20, 30);
//	  
//	  String subject = "Meeting Subject";
//	  String location = "Location - Mumbai";
//	  String description = "This goes in decription section of the metting like agenda etc.";
//	  
//	  String hostEmail = "shaelynjoy@gmail.com";
//	  
//	  //Creating a new calendar
//	  net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
//
//	  
//	  SimpleDateFormat sdFormat =  new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
//	  String strDate = sdFormat.format(startCal.getTime());
//	  
//	  net.fortuna.ical4j.model.Date startDt = null;
//	  try {
//	   startDt = new net.fortuna.ical4j.model.Date(strDate,"yyyyMMdd'T'hhmmss'Z'");
//	  } catch (ParseException e) {
//	   e.printStackTrace();
//	  }
//	  
//	  long diff = endCal.getTimeInMillis() - startCal.getTimeInMillis();
//	  int min = (int)(diff / (1000 * 60));
//	  
//	  Dur dur = new Dur(0,0,min,0);
//	  
//	  //Creating a meeting event
//	  VEvent meeting = new VEvent(startDt,dur,subject);
//	  
//
//	  meeting.getProperties().add(new Description());
//	  
////	  try {
////	   meeting.getProperties().getProperty(Property.DESCRIPTION).setValue(description);
////	  } catch (IOException e) {
////	   e.printStackTrace();
////	  } catch (URISyntaxException e) {
////	   e.printStackTrace();
////	  } catch (ParseException e) {
////	   e.printStackTrace();
////	  }
////	  
////	  try {
////	   meeting.getProperties().add(new Organizer("MAILTO:"+hostEmail));
////	  } catch (URISyntaxException e) {
////	   e.printStackTrace();
////	  }
//	  
//	  calendar.getComponents().add(meeting);
		
		// Create a TimeZone
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		TimeZone timezone = registry.getTimeZone("America/Mexico_City");
		VTimeZone tz = timezone.getVTimeZone();

		 // Start Date is on: April 1, 2008, 9:00 am
		java.util.Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(timezone);
		startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
		startDate.set(java.util.Calendar.YEAR, 2008);
		startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
		startDate.set(java.util.Calendar.MINUTE, 0);
		startDate.set(java.util.Calendar.SECOND, 0);

		 // End Date is on: April 1, 2008, 13:00
		java.util.Calendar endDate = new GregorianCalendar();
		endDate.setTimeZone(timezone);
		endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
		endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
		endDate.set(java.util.Calendar.YEAR, 2008);
		endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
		endDate.set(java.util.Calendar.MINUTE, 0);	
		endDate.set(java.util.Calendar.SECOND, 0);

		// Create the event
		String eventName = "Progress Meeting";
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		VEvent meeting = new VEvent(start, end, eventName);

		// add timezone info..
		meeting.getProperties().add(tz.getTimeZoneId());

		// generate unique identifier..
		UidGenerator ug;
		try {
			ug = new UidGenerator("uidGen");
			Uid uid = ug.generateUid();
			meeting.getProperties().add(uid);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// Create a calendar
		net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(CalScale.GREGORIAN);


		// Add the event and print
		icsCalendar.getComponents().add(meeting);
		System.out.println(icsCalendar);
		 
		 return "works";
	}
	
}
