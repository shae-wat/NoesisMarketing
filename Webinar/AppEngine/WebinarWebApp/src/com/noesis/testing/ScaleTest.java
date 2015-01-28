package com.noesis.testing;

import java.io.IOException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.noesis.webinar.WebinarConnector;
import com.noesis.webinar.WebinarUser;

public class ScaleTest{
	
	public ScaleTest(Integer nameInt) throws EntityNotFoundException{
		WebinarConnector wc = new WebinarConnector();
		String webinarId = "4762536079313296641";
		WebinarUser user = new WebinarUser();
		user.setFirstName("Testtest");
		user.setLastName(nameInt.toString());
		user.setEmail(nameInt.toString()+"@testtest.com");
		try {
			wc.registerUser(webinarId, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void runScaleTests(int numTests) throws EntityNotFoundException{
		ScaleTest st;
		for(int i = 0; i<numTests; i++){
			System.out.println(i);
			Integer testNum = (Integer)i;
			st = new ScaleTest(testNum);
		}
	}
	
	public static void main(String [] args){
		Integer numUsers = 60; //number of users to emulate w scale test (hard coded)
		try {
			runScaleTests(numUsers);
			System.out.println("Scale testing for " + numUsers.toString() + " users was SUCCESSFUL");
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}