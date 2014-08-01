package com.noesis.webinar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
			//Check for test webinars, which must include TEST in all caps in their title
			if(!webinar.getSubject().contains("TEST"))
				officialWebinars.add(webinar);
		}
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
	
	
	public WebinarUser registerUser(String webinarId, WebinarUser user) throws IOException{

		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/"+ webinarId +"/registrants");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());
		//System.out.println("\nPOST registrant(s) to webinarId = " + webinarId);
		
			try {
				System.out.println("\nRegistering " + user.getFirstName() + " for " + webinarId);

				String registrationJSON = "{\"firstName\":\""+ user.getFirstName()+"\",\"lastName\":\""+ user.getLastName()+"\",\"email\":\""+ user.getEmail()+"\"}";
				//String message = URLEncoder.encode(registrationJSON, "UTF-8");
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
	
//	public String utf8String(String str) throws UnsupportedEncodingException{
//		byte ptext[] = str.getBytes("UTF-8");
//		String value = new String(ptext, "UTF-8");
//		return value;
//	}
	
}
