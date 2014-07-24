package com.noesis.webinar;

import java.util.ArrayList;
import java.util.Map;

public class WebinarData {

	public String webinarKey;
	public String subject;
	public String description;
	public ArrayList<Map<String,String>> times;
	public String timeZone;

	public String getWebinarKey() {
		return webinarKey;
	}

	public void setWebinarKey(String webinarKey) {
		this.webinarKey = webinarKey;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public ArrayList<Map<String, String>> getTimes() {
		return times;
	}

	public void setTimes(ArrayList<Map<String, String>> times) {
		this.times = times;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	
}
