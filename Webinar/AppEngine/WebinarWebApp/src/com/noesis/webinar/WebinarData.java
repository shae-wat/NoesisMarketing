package com.noesis.webinar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WebinarData implements Comparable<WebinarData>{

	public String webinarKey;
	public String subject;
	public String description;
	public ArrayList<Map<String,String>> times;
	public ArrayList<DateAndTime> datesAndKeys = new ArrayList<DateAndTime>();
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
	
	public Map<String, String> getEarliestStartTime() {
		return times.get(0);
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
		when1 = new DateAndTime(webinar.getEarliestStartTime());
		when2 = new DateAndTime(this.getEarliestStartTime());
		//System.out.println(" when2.dateObj.compareTo(when1.dateObj) = " + when2.dateObj.compareTo(when1.dateObj));
		return when2.dateObj.compareTo(when1.dateObj);
	}

	public ArrayList<DateAndTime> getDatesAndTimes() {
		Collections.sort(datesAndKeys);
		return datesAndKeys;
	}

	public void setDatesAndTimes(ArrayList<DateAndTime> datesAndTimes) {
		this.datesAndKeys = datesAndTimes;
	}
	
}
