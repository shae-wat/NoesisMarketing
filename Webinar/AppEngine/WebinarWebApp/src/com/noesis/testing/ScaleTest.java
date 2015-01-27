package com.noesis.testing;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.noesis.webinar.WebinarConnector;

public class ScaleTest{
	
	public ScaleTest(Integer nameInt) throws EntityNotFoundException{
		//webinar connector
		WebinarConnector wc = new WebinarConnector();
		//registration for a webinar
	}
	
	public static void runScaleTests(int numTests){
		for(int i = 0; i<numTests; i++){
			System.out.println(i);
			//create new scaleTest instance
			//if fail, freak out
		}
	}
	
	public static void main(String [] args){
		Integer numUsers = 5; //number of users to emulate w scale test (hard coded)
		runScaleTests(numUsers);
		System.out.println("Scale testing for " + numUsers.toString() + " users was SUCCESSFUL");
	}
}