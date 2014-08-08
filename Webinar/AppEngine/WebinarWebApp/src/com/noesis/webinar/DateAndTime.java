package com.noesis.webinar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class DateAndTime {

	public List<String> days = new ArrayList<String>();
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

	public String day;
	public List<String> dates = new ArrayList<String>();
	public String date;
	public String startTime;
	public String endTime;
	public List<String> startTimes = new ArrayList<String>();
	public List<String> endTimes = new ArrayList<String>();
	public Date dateObj;
	public List<Date> dateObjList = new ArrayList<Date>();
	
	public DateAndTime(Map<String,String> data) throws ParseException {

		System.out.println("*data = " + data);
		Date startDate = null;
		Date endDate = null;

		Iterator<Map.Entry<String,String>> it = data.entrySet().iterator();
		int counter = 0;
	    while (it.hasNext()) {
	        Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
	        //System.out.println(pairs.getKey() + " *= " + pairs.getValue());
	        if (counter == 0){
	        	startTime = pairs.getValue();
	        	startDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0100"));
	        	dateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "-0100"));
	        }
	        else if (counter == 1){
	        	endTime= pairs.getValue();
	        	endDate = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTime.replaceAll("Z$", "-0100"));
	        }
	        counter++;
	    }

		
		day = (new SimpleDateFormat("EEEEEEEE")).format(startDate);
		//System.out.println("**day = " + day);

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
	
	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) {
		this.days = days;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public List<String> getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(List<String> startTimes) {
		this.startTimes = startTimes;
	}

	public List<String> getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(List<String> endTimes) {
		this.endTimes = endTimes;
	}

	public List<Date> getDateObjList() {
		return dateObjList;
	}

	public void setDateObjList(List<Date> dateObjList) {
		this.dateObjList = dateObjList;
	}

}
