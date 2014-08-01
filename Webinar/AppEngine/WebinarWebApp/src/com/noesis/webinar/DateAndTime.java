package com.noesis.webinar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;


public class DateAndTime {

	public String day;
	public String date;
	public String startTime;
	public String endTime;
	
	public DateAndTime(ArrayList<Map<String,String>> data) throws ParseException {

		System.out.println("\n\n DATE AND TIME");
		Date startDate = null;
		Date endDate = null;
		for (Map<String,String> ele : data){
			System.out.println(ele);
			Iterator it = ele.entrySet().iterator();
			int counter = 0;
		    while (it.hasNext()) {
		        Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
		        //System.out.println(pairs.getKey() + " *= " + pairs.getValue());
		        if (counter == 0){
		        	startTime = pairs.getValue();
		        	startDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0100"));
		        }
		        else if (counter == 1){
		        	endTime= pairs.getValue();
		        	endDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTime.replaceAll("Z$", "-0100"));
		        }
		        it.remove(); // avoids a ConcurrentModificationException
		        counter++;
		    }
			
		}
		
		
		String defaultTimezone = TimeZone.getDefault().getID();
		System.out.println("defaultTimezone: " + defaultTimezone);
		
		date = (new SimpleDateFormat("MMM' 'dd' 'yyyy")).format(startDate);
		System.out.println("date = " + date);
		
		startTime = (new SimpleDateFormat("HH:mm")).format(startDate);
		System.out.println("startTime = " + startTime);
		
		endTime = (new SimpleDateFormat("HH:mm")).format(endDate);
		System.out.println("endTime = " + endTime);
		
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

}
