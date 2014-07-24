package com.noesis.webinar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
		}
		
		return upcomingWebinars;

	}
	
	public List<WebinarUser> registerUsers(String webinarId,List<WebinarUser> listUsers) throws IOException{

			URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/"+ webinarId +"/registrants");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", "OAuth oauth_token="+wa.getAccess_token());

			for (WebinarUser wu:listUsers){
				try {
					System.out.println("\nRegistering " + wu.getFirstName() + " for " + webinarId);
	
					String registrationJSON = "{\"firstName\":\""+wu.getFirstName()+"\",\"lastName\":\""+wu.getLastName()+"\",\"email\":\""+wu.getEmail()+"\"}";
					String message = URLEncoder.encode(registrationJSON, "UTF-8");
					OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		            writer.write(message);
		            writer.close();
		    
		            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		            	System.out.println("OK");
		            } else {
		            	System.out.println("Error: Server returned HTTP error code");
		            }

					//Registered user = {"registrantKey":106478128,"joinUrl":"https://www4.gotomeeting.com/join/872916911/106478128"}
					//add to WebinarUser
			} catch (MalformedURLException e) {
				e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listUsers;
	}
	
}
