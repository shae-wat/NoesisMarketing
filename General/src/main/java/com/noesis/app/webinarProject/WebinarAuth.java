package com.noesis.app.webinarProject;

public class WebinarAuth {
	
	/*Tokens needed to sign a user into the webinar*/
	String access_token = "";
	String organizer_key = "";

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getOrganizer_key() {
		return organizer_key;
	}

	public void setOrganizer_key(String organizer_key) {
		this.organizer_key = organizer_key;
	}
	
	

}
