package com.noesis.app;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;

import com.google.gson.Gson;
import com.noesis.app.webinarData.WebinarAuth;

public class Demo {

	public static void main (String args[])
	{
		try {
			
			String response = "";
			Gson gson = new Gson();
			
			//call GoToWebinar API, returns JSON use keys to register users for the webinar
			response = Utils.httpsGet("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
			WebinarAuth wa = gson.fromJson(response, WebinarAuth.class);
			System.out.println("access token = " + wa.getAccess_token());
			
			//TODO : see if can get to work
			//use CURL examples //get-attendee
			//response = Utils.httpsGet("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/webinars/" + "919758823"  + "/registrants?access_token=" + wa.getAccess_token() );
			
			System.out.println("response = " + response);
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
}
