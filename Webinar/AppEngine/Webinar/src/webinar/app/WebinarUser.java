package webinar.app;

import java.util.HashMap;
import java.util.Map;

public class WebinarUser {
	
	public String firstName;
	public String lastName;
	public String email;
	public String registrantKey = "";
	public String joinUrl = "";
	
	//map webinarID to userUniqueWebinarID
	Map<String, String> web2UserID = new HashMap<String, String>();
	
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
		this.registrantKey = registrantKey;
	}

	public String getJoinUrl() {
		return joinUrl;
	}

	public void setJoinUrl(String joinUrl) {
		this.joinUrl = joinUrl;
	}

}
