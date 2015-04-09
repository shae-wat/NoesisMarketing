package com.noesis.testing;

import java.io.IOException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.noesis.webinar.WebinarConnector;
import com.noesis.webinar.WebinarUser;

public class ScaleTest{
	
	private static boolean failed;
	
	public ScaleTest(Integer nameInt) throws EntityNotFoundException{
		
		WebinarConnector wc = new WebinarConnector();
		String webinarId = "5805175468271867394";
		WebinarUser user = new WebinarUser();
		user.setFirstName("Test");
		user.setLastName(nameInt.toString());
		user.setEmail(nameInt.toString()+"@test.com");
		try {
			wc.registerUser(webinarId, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			failed = true;
			e.printStackTrace();
		}
	}
	
	public static void runScaleTests(int numTests) throws EntityNotFoundException{
		ScaleTest st;
		for(int i = 0; i<numTests; i++){
			System.out.println(i);
			Integer testNum = (Integer)i;
			st = new ScaleTest(testNum);
			if (st.failed)
				break;
		}
	}
	
	public static void main(String [] args){
		Integer numUsers = 400; //number of users to emulate w scale test (hard coded)
		try {
			runScaleTests(numUsers);
			System.out.println("\n\nScale testing for " + numUsers.toString() + " users was SUCCESSFUL");
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}