package com.formValidation.app;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	//Read from file of form URLs
    	FileInputStream inputText = new FileInputStream("formURLS.txt");
    	BufferedReader br = new BufferedReader(new InputStreamReader(inputText));
    	List<String> urlList = new ArrayList<String>();
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		urlList.add("https://www.noesisenergy.com/site/" + line);
    		//System.out.println("https://www.noesisenergy.com/site/" + line);
    	}
     
    	br.close();
    	
    	
    	List<String> fieldList = new ArrayList<String>();
    	String sourceField = "";
    	
    	for(String url : urlList){
    	 Document doc = Jsoup.connect(url).get();
         Elements inputs = doc.select("input");
         Elements selects = doc.select("select");
         System.out.println("\n========\nForm url: " + url + "\n");
         fieldList.clear();
         int numFields = 0;
         
         for(Element input : inputs) {          
             fieldList.add(input.attr("name"));
             fieldList.add(input.attr("ng-init"));
             if(input.attr("ng-init").startsWith("Source__c=")){
            	 sourceField = input.attr("ng-init").substring(9);
             }
         }
         for(Element select : selects) {          
             fieldList.add(select.attr("name"));
             
         }
         
         
         /*  What is the value of the hidden 'source' field?  */
         System.out.println("Value of the hidden 'source' field: " + sourceField);
         
         
         /*  What is the text of the opt in question?  */
         Elements otros = doc.select("p");
         for (Element p : otros){
        	 boolean optInQ = false;
             if(fieldList.contains("Landing_Page_Opt_In__c") && !p.select("p[style=color:#FFFFFF;]").toString().equals("") && !p.select("p[style=color:#FFFFFF;]").toString().contains("All Fields Required")){
            	 optInQ = true;
            	 System.out.println("\n'Opt in' question on the form: " + optInQ);
            	 numFields += 1;
            	 System.out.println("Opt in Q numFields = " + numFields);
             }
        	 if (!p.select("p[style=color:#FFFFFF;]").toString().equals("") && !p.select("p[style=color:#FFFFFF;]").toString().contains("All Fields Required"))
        			 System.out.println("Opt in question:  " + formatOptInQ(p.select("p[style=color:#FFFFFF;]").toString()));
         }
         
    	
         /*  How many fields are on the form?  */
         boolean formHtml = false;
         boolean sellOptin = false;
         Elements fields = doc.select("div");
         for(Element f : fields){
        	 if (!f.attr("ng-include").toString().equals("")){
        		 System.out.println("\nPage ng-include's " + f.attr("ng-include").toString()); 
        		 numFields += 10;
        		 //System.out.println("numFields = " + numFields);
        		 formHtml = true;
        	 }
         }
         for (String field : fieldList){
        	 //System.out.println(field);
        	 if(!formHtml){
	        	 if(field.equals("First_Name_Casual__c")){
	        		 numFields += 1;
	        		 System.out.println("First_Name_Casual__c numFields = " + numFields);
	        	 }
	        	 if(field.equals("LastName")){
	        		 numFields += 1;
	        		 System.out.println("LastName numFields = " + numFields);
	        	 }
	        	 if(field.equals("Email")){
	        		 numFields += 1;
	        		 System.out.println("Email numFields = " + numFields);
	        	 }
	        	 if(field.equals("Phone")){
	        		 numFields += 1;
	        		 System.out.println("Phone numFields = " + numFields);
	        	 }
	        	 if(field.equals("Company")){
	        		 numFields += 1;
	        		 System.out.println("Company numFields = " + numFields);
	        	 }
	        	 if(field.equals("Company_Headquarter_State__c")){
	        		 numFields += 1;
	        		 System.out.println("Company_Headquarter_State__c numFields = " + numFields);
	        	 }
	        	 if(field.equals("Company_Size__c")){
	        		 numFields += 1;
	        		 System.out.println("Company_Size__c numFields = " + numFields);
	        	 }
	        	 if(field.equals("Sell_Side_Opt_In__c") && !sellOptin){
	        		 numFields += 1;
	        		 sellOptin = true;
	        		 System.out.println("Sell_Side_Opt_In__c numFields = " + numFields);
	        	 }
        	 }
        	 
        	 
         }
    	if (numFields == 0){
    		System.out.println("XX Num fields = " + numFields);
    		
    	}
    	else
    		System.out.println("Num fields = " + numFields);
    		
    	
         
    	/*  Does the form include the Google Analytics fields?  */
         boolean analyticsQ = false;
         if(fieldList.contains("GASourceLatest__c") && fieldList.contains("GAMediumLatest__c") && fieldList.contains("GACampaignLatest__c")){
        	 analyticsQ = true;
         }
         System.out.println("\nIncludes the Google Analytics fields: " + analyticsQ);
    	
         
    	
    	/*  Is there an 'opt in' question on the form? What is the text of the opt in question?  */
        
         
     
         
         
         
        // end loop over urls 
    	}
    	System.out.println( "\n======Form validation complete" );
    }
    
    private static String formatOptInQ(String s){
    	
    	return s.substring(27,s.length()-4);
    }
}
