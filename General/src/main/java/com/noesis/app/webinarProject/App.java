package com.noesis.app.webinarProject;

import java.util.ArrayList;
import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//Authorize user
    	WebinarConnector wc = new WebinarConnector();
    	
    	WebinarUser user1 = new WebinarUser("User1", "Lastname1", "shaelynjoy@gmail.com");
    	WebinarUser user2 = new WebinarUser("User2", "Lastname2", "swatson@noesis.com");
    	List<WebinarUser> userList = new ArrayList<WebinarUser>();
    	userList.add(user1);
    	userList.add(user2);
    	
    	String webinarId = "767279767";
    	
    	//wc.registerUsers(webinarId, userList);
    	wc.getUpcomingWebinars();
    	
        System.out.println( "Completed run of WebinarConnector" );
    }
}
