package com.noesis.webinar;

import java.util.HashMap;
import java.util.Map;

/* Created for each instance of a webinar registration */
public class WebinarUser {

	public String firstName;
	public String lastName;
	public String email;
	public String registrantKey = "";
	public String joinUrl = "";

	public WebinarUser(){}
	//map webinarID to userUniqueWebinarID
	//Map<String, String> web2UserID = new HashMap<String, String>();

	//HashMPap<webinarData, webinarPoll>
	//Map<WebinarData, WebinarPoll> = new HashMap<WebinarData, WebinarPoll>();

	public WebinarUser(String first, String last, String e) {
		this.firstName = first;
		this.lastName = last;
		this.email = e;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistrantKey() {
		return registrantKey;
	}

	public void setRegistrantKey(String registrantKey) {
		System.out.println("setting reg key to = " + registrantKey);
		this.registrantKey = registrantKey;
	}

	public String getJoinUrl() {
		return joinUrl;
	}

	public void setJoinUrl(String joinUrl) {
		System.out.println("setting join url to = " + joinUrl);
		this.joinUrl = joinUrl;
	}
	
}
