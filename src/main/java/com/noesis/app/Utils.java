package com.noesis.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Utils {
	
	public static String readFromURL (String urlStr) throws Exception
	{
		// replace any spaces
		urlStr = urlStr.replace(" ", "%20");
		
		String result = "";
		
			
			URL url = new URL(urlStr);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(url.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        {
	            // System.out.println(inputLine);
	            result = result + inputLine;
	        }
	        in.close();
        
		
		
		return result;
	}
	
}
