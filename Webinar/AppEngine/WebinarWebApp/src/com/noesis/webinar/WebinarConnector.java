package com.noesis.webinar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebinarConnector {

	WebinarAuth wa = new WebinarAuth();
	Gson gson = new Gson(); 
	
	public WebinarConnector () throws EntityNotFoundException
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key authKey = KeyFactory.createKey("webinarAuth", "goto_access");
		Entity accessToken = datastore.get(authKey);
		System.out.println("**ENTITY.tostring = " + accessToken.toString());
		
		Calendar calendar = new GregorianCalendar();
		Date now = calendar.getTime();
		System.out.println("The time right now is: " + calendar.getTime().toString());
		Date expirationTime = (Date) accessToken.getProperty("expiration");
		System.out.println("Access token expiration: " + expirationTime.toString());
		
		if(now.compareTo(expirationTime) < 0){
			System.out.println("Access token has not expired");
			wa.setAccess_token(accessToken.getProperty("access_token").toString());
			wa.setOrganizer_key("300000000000341311");
			System.out.println("access_token = " + wa.getAccess_token());				
			System.out.println("organizer_key = " + wa.getOrganizer_key());
		}
		else{			
			//Regenerate access_token through GoToWebinar
			try {
			    URL url = new URL("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
			    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			    String line ="";
			    String rline = "";
	
			    while ((rline = reader.readLine()) != null) {
			        line = line + rline;
			    }
			    reader.close();
			    
			    wa = gson.fromJson(line, WebinarAuth.class);
				System.out.println("\nGET auth data = " + line);
				System.out.println("access token = " + wa.getAccess_token());				
				System.out.println("organizer key = " + wa.getOrganizer_key());
				
				//update access_token key
				System.out.println("Updating datastored accessToken to: " + wa.getAccess_token());
				accessToken.setProperty("access_token", wa.getAccess_token());
				
				//update expiration key
				calendar.add(Calendar.DAY_OF_MONTH, 1); //access token expiration is set for one day from now
				System.out.println("Expiration set for: " + calendar.getTime().toString());
				accessToken.setProperty("expiration", calendar.getTime());
				
				//update datastore
				datastore.put(accessToken);
	
			} catch (MalformedURLException e) {
			    // ...
			} catch (IOException e) {
			    // ...
			}
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
			System.out.println("\nGET upcoming webinars = "+line+"/n");
			upcomingWebinars = gson.fromJson(line, new TypeToken<List<WebinarData>>(){}.getType());
		}
		in.close();
		
		for (WebinarData webinar:upcomingWebinars){
			System.out.println(webinar.getSubject() + " : " + webinar.getWebinarKey());
			DateAndTime dateAndKey = null;
			
			dateAndKey = new DateAndTime(webinar.times.get(0), webinar.getTimeZone());  //shouldbe only ele at this point
			dateAndKey.setWebinarKey(webinar.getWebinarKey());
			//System.out.println(dateAndKey.getStartTime());
			System.out.println(webinar.getTimeZone() + "\n");
			dateAndKey.setTimeZone(webinar.getTimeZone());
			
			//Remove list duplicates
			for(WebinarData web:officialWebinars){
				if (webinar.getSubject().equals(web.getSubject())){
					//System.out.println("Added " + webinar.getSubject() + " times to " + web.getSubject());
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
	
}
