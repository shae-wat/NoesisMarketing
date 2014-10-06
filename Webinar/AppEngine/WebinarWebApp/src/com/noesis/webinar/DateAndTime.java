package com.noesis.webinar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class DateAndTime implements Comparable<DateAndTime>{


	public String day;
	public String date;
	public String startTime;
	public String endTime;
	public String monthNum;
	public String year;
	public String ampm;
	
	public Date dateObj;
	public Date startDateObj;
	public Date endDateObj;
	
	
	public String webinarKey;
	public String timeZone;
	
	public DateAndTime(Map<String,String> data, String timeZone) {

		
		int c = 0;
		for (Map.Entry<String, String> entry : data.entrySet())
		{
		    System.out.println("\n"+ entry.getKey() + ": " + entry.getValue() + " " + timeZone);
		    if (c==0){
		    	String startTiempo = entry.getValue();
		    	String[] startInfo0 = startTiempo.split("-");
		    	String[] startInfo1 = startInfo0[2].split("T");
		    	
		    	year = startInfo0[0];
		    	monthNum = startInfo0[1];
		    	date = monthNum + "/" + startInfo1[0];
		    	String sTime = startInfo1[1];
		    	
		    	System.out.println("year = " + year);
		    	System.out.println("monthNum = " + monthNum);
		    	System.out.println("date = " + date);
		    	System.out.println("sTime = " + sTime);
		    	int h = Integer.parseInt(sTime.substring(0, 2));
		    	System.out.println("h = " + h);
		    	String m = sTime.substring(3, 5);
		    	System.out.println("m = " + m);
		    	
		    	
			    if (timeZone.equals("EDT")){
			    	h = h - 16;
			    	ampm = "pm";
			    	if(h < 0){
			    		h = h + 12;
			    		ampm ="am";
			    	}
			    	if(h==0)
			    		h=12;
			    	startTime = Integer.toString(h) + ":" + m + ampm;
			    	System.out.println("startTime = " + startTime + ampm);
			    	try {
						startDateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTiempo.replaceAll("Z$", "-0100"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
			    if (timeZone.equals("CDT")){
			    	h = h - 17;
			    	ampm ="pm";
			    	if(h < 0){
			    		h = h + 12;
			    		ampm ="am";
			    	}
			    	if(h==0)
			    		h=12;
			    	startTime = Integer.toString(h) + ":" + m + ampm;
			    	System.out.println("startTime = " + startTime + ampm);
			    	try {
						startDateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTiempo.replaceAll("Z$", "-0000"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
			    dateObj = startDateObj;
		    }
		    if (c==1){
		    	String endTiempo = entry.getValue();
		    	String[] endInfo0 = endTiempo.split("-");
		    	String[] endInfo1 = endInfo0[2].split("T");
		    	
		    	String eTime = endInfo1[1];

		    	System.out.println("end time = " +eTime);
		    	int h = Integer.parseInt(eTime.substring(0, 2));
		    	System.out.println("h = " + h);
		    	String m = eTime.substring(3, 5);
		    	System.out.println("m = " + m);
		    	
		    	
			    if (timeZone.equals("EDT")){
			    	h = h - 16;
			    	ampm ="pm";
			    	if(h < 0){
			    		h = h + 12;
			    		ampm ="am";
			    	}
			    	if(h==0)
			    		h=12;
			    	endTime = Integer.toString(h) + ":" + m + ampm;
			    	System.out.println("endTime = " + endTime + ampm);
			    	try {
						endDateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTiempo.replaceAll("Z$", "-0100"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
			    if (timeZone.equals("CDT")){
			    	h = h - 17;
			    	ampm ="pm";
			    	if(h < 0){
			    		h = h + 12;
			    		ampm ="am";
			    	}
			    	if(h==0)
			    		h=12;
			    	endTime = Integer.toString(h) + ":" + m + ampm;
			    	System.out.println("endTime = " + endTime + ampm);
			    	try {
						endDateObj = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(endTiempo.replaceAll("Z$", "-0000"));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
		    	
		    }
		    c++;
		}

		
		day = (new SimpleDateFormat("EEEEEEEE")).format(startDateObj);
		System.out.println("day = " + day);
		
		if (startTime.startsWith("0"))
			startTime = startTime.substring(1);
		System.out.println("startTime = " + startTime);

		
		if (endTime.startsWith("0"))
			endTime = endTime.substring(1);
		System.out.println("endTime = " + endTime);

		
	}
	
	public String getDay() {
		return day;
	}
	
	public String getPresentableDay() {
		String pDay = "";
        switch (day) {
            case "Monday":
                 pDay = "Mon";
                 break;
            case "Tuesday":
            	 pDay = "Tues";
            	 break;
            case "Wednesday":
            	 pDay = "Wed";
            	 break;
            case "Thursday":
            	 pDay = "Thurs";
            	 break;
            case "Friday":
            	 pDay = "Fri";
            	 break;
            case "Saturday":
            	 pDay = "Sat";
            	 break;
            case "Sunday":
            	 pDay = "Sun";
            	 break;
        }
        return pDay;
		
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDate() {
		return date;
	}
	
	public String getPresentableDate(){
		String presDate = date;
		
		if(date.charAt(0) == '0'){
			presDate = date.substring(1);
		}
		//System.out.println("*****presDate = " + presDate);
		return presDate;
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
	
	public String getLongTimeZone(){
		return timeZone;
	}
	
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int compareTo(DateAndTime d){
		return this.getDateObj().compareTo(d.getDateObj());
	}

	public Date getStartDateObj() {
		return startDateObj;
	}

	public void setStartDateObj(Date startDateObj) {
		this.startDateObj = startDateObj;
	}

	public Date getEndDateObj() {
		return endDateObj;
	}

	public void setEndDateObj(Date endDateObj) {
		this.endDateObj = endDateObj;
	}

}
