package com.noesis.app.webinarData;

public class WebinarUser {
	
	public String firstName;
	public String lastName;
	public String email;
	public String sfdcID;
	
	public WebinarUser(String first, String last, String e, String sID) {
		this.firstName = first;
		this.lastName = last;
		this.email = e;
		this.sfdcID = sID;
	}

}
