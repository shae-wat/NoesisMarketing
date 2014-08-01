package com.noesis.webinar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

public class WebinarData implements Comparable<WebinarData>{

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
	
	public int compareTo(WebinarData webinar){
		DateAndTime when1 = null;
		DateAndTime when2 = null;
		try {
			when1 = new DateAndTime(webinar.getTimes());
			when2 = new DateAndTime(this.getTimes());
			System.out.println("****when1 = " + when1.dateObj + " when2 = " + when2.dateObj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println(" when2.dateObj.compareTo(when1.dateObj) = " + when2.dateObj.compareTo(when1.dateObj));
		return when2.dateObj.compareTo(when1.dateObj);
	}
	
}
