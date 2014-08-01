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
		for (Map<String,String> ele : data){
			System.out.println(ele);
			Iterator it = ele.entrySet().iterator();
			int counter = 0;
		    while (it.hasNext()) {
		        Map.Entry<String,String> pairs = (Map.Entry<String,String>)it.next();
		        //System.out.println(pairs.getKey() + " *= " + pairs.getValue());
		        if (counter == 0)
		        	startTime = pairs.getValue();
		        else if (counter == 1)
		        	endTime= pairs.getValue();
		        it.remove(); // avoids a ConcurrentModificationException
		        counter++;
		    }
			
		}

		System.out.println("*startTime = " + startTime);
		System.out.println("*endTime = " + endTime);
		
		
		String defaultTimezone = TimeZone.getDefault().getID();
		Date date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).parse(startTime.replaceAll("Z$", "+0100"));


		System.out.println("string: " + startTime);
		System.out.println("defaultTimezone: " + defaultTimezone);
		System.out.println("date: " + (new SimpleDateFormat("MM-dd-yyyy' 'HH:mm:ss")).format(date));
		

	}
	
	

}
