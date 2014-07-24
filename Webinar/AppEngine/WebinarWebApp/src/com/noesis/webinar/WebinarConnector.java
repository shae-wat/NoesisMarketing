package com.noesis.webinar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

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
			System.out.println("GET auth data = " + line);
			System.out.println("access token = " + wa.getAccess_token());
			System.out.println("organizer key = " + wa.getOrganizer_key());

		} catch (MalformedURLException e) {
		    // ...
		} catch (IOException e) {
		    // ...
		}
	}
	
	public void getUpcomingWebinars () throws IOException
	{
		URL url = new URL("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/upcomingWebinars");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		String oa = "OAuth oauth_token";
		connection.setRequestProperty("charset", "utf-8" );
		connection.addRequestProperty(oa, wa.getAccess_token());  //addRequestProperty
//		
		Map<String,List<String>> props = new HashMap<String,List<String>>();
		props = connection.getRequestProperties();
		for (Entry<String, List<String>> entry : props.entrySet()){
			System.out.println("key = " + entry.getKey());
			for (String li : entry.getValue()){
				System.out.println(li);
			}
			System.out.println("\n");
		}
		
		connection.connect();
		
		
		
		String line = "";
		
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while((line = in.readLine()) != null)
		{
			System.out.println(line);
		}
		BufferedReader err = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		while((line = err.readLine()) != null)
		{
			System.out.println(line);
		}
		
	}
	
}
