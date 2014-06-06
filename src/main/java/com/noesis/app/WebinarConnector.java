package com.noesis.app;

/*
 * Registers a user for a webinar on GoToWebinar using given information
 */

public class WebinarConnector {
	
	public WebinarConnector() {
		System.out.println("Webinar Constructer");
		
		String contactURL = "http://www.iflscience.com/brain/five-cool-ways-trick-your-brain";
		
		 // /rest/{service name}  
		try {
			String result = Utils.readFromURL(contactURL);
			System.out.println("result = " + result);
			System.out.println("Utils.readFromURL successful");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Utils.readFromURL unsuccessful");
		}
	}
	

}
