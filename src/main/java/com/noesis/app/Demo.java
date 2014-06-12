package com.noesis.app;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;

public class Demo {

	public static void main (String args[])
	{
		try {
			
			String response = "";
			
			response = Utils.httpsGet("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
			
			System.out.println(response);
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
}
