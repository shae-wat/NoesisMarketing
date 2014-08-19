package com.noesis.webinar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class DateAndTime implements Comparable<DateAndTime>{


	public String day;
	public String date;
	public String startTime;
	public String endTime;
	public Date dateObj;
	public String webinarKey;
	public String timeZone;
	
	public DateAndTime(Map<String,String> data, String timeZone) {

		Date startDate = null;
		Date endDate = null;

		Iterator<Map.Entry<String,String>> it = data.entrySet().iterator();
		int counter = 0;
	    while (it.hasNext()) {
	        Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
	        //System.out.println(pairs.getKey() + " *= " + pairs.getValue());
	        if (counter == 0){
	        	startTime = pairs.getValue();
	        	try {
	        		if (timeZone.equals("CDT")){
	        			startDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0000"));
						dateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0000"));
	        		}
	        		else if (timeZone.equals("EDT")){
	        			startDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0100"));
						dateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0100"));
	        		}
	        		else{
	        			startDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0000"));
						dateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0000"));
	        		}
        		} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else if (counter == 1){
	        	endTime= pairs.getValue();
	        	try {
	        		if (timeZone.equals("CDT")){
	        			endDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTime.replaceAll("Z$", "-0000"));
	        		}
	        		else if (timeZone.equals("EDT")){
	        			endDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTime.replaceAll("Z$", "-0100"));	
	        		}
	        		else{
	        			endDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTime.replaceAll("Z$", "-0000"));
	        		}
        		} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        counter++;
	    }

		
		day = (new SimpleDateFormat("EEEEEEEE")).format(startDate);
		//System.out.println("day = " + day);

		date = (new SimpleDateFormat("MMM' 'dd,' 'yyyy")).format(startDate);
		//System.out.println("date = " + date);
		
		startTime = (new SimpleDateFormat("hh:mm a")).format(startDate);
		if (startTime.startsWith("0"))
			startTime = startTime.substring(1);
		//System.out.println("startTime = " + startTime);

		
		endTime = (new SimpleDateFormat("hh:mm a")).format(endDate);
		if (endTime.startsWith("0"))
			endTime = endTime.substring(1);
		//System.out.println("endTime = " + endTime);

		
	}
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Date getDateObj() {
		return dateObj;
	}

	public void setDateObj(Date dateObj) {
		this.dateObj = dateObj;
	}
	
	public String getWebinarKey() {
		return webinarKey;
	}

	public void setWebinarKey(String webinarKey) {
		this.webinarKey = webinarKey;
	}
	
	public String getTimeZone(){
		if (timeZone.equals("America/Chicago"))
			return "CDT";
		else if (timeZone.equals("America/New_York"))
			return "EDT";
		else
			return timeZone;
	}
	
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int compareTo(DateAndTime d){
		return this.getDateObj().compareTo(d.getDateObj());
	}

}
