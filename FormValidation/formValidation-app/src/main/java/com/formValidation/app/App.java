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
         System.out.println("\n========\nForm url: " + url + "\n");
         
         for(Element input : inputs) {          
             fieldList.add(input.attr("name"));
             fieldList.add(input.attr("ng-init"));
             if(input.attr("ng-init").startsWith("Source__c=")){
            	 sourceField = input.attr("ng-init").substring(9);
             }
         }
         
    	
         /*  How many fields are on the form?  */
         int numFields = 0;
         Elements fields = doc.select("div");
         for(Element f : fields){
        	 if (!f.attr("ng-include").toString().equals("")){
        		 System.out.println("Page ng-include's " + f.attr("ng-include").toString()); 
        		 numFields += 11;
        	 }
         }
    	if (numFields == 0){
    		System.out.println("XX Num fields = " + numFields);
    		break;
    	}
    	else
    		System.out.println("Num fields = " + numFields);
    		
    	
         
    	/*  Does the form include the Google Analytics fields?  */
         boolean analyticsQ = false;
         if(fieldList.contains("GASourceLatest__c") && fieldList.contains("GAMediumLatest__c") && fieldList.contains("GACampaignLatest__c")){
        	 analyticsQ = true;
         }
         System.out.println("\nDoes the form include the Google Analytics fields? " + analyticsQ);
    	
    	
    	/*  What is the value of the hidden 'source' field?  */
         System.out.println("\nWhat is the value of the hidden 'source' field? " + sourceField);
         
    	
    	/*  Is there an 'opt in' question on the form? What is the text of the opt in question?  */
         boolean optInQ = false;
         if(fieldList.contains("Landing_Page_Opt_In__c")){
        	 optInQ = true;
         }
         System.out.println("\nIs there an 'opt in' question on the form? " + optInQ);
         
         
         /*  What is the text of the opt in question?  */
         Elements otros = doc.select("p");
         for (Element p : otros){
        	 if (!p.select("p[style=color:#FFFFFF;]").toString().equals(""))
        			 System.out.println("\nOpt in question = " + formatOptInQ(p.select("p[style=color:#FFFFFF;]").toString()));
         }
         
         
         
        // end loop over urls 
    	}
    	System.out.println( "\n======Form validation complete" );
    }
    
    private static String formatOptInQ(String s){
    	
    	return s.substring(27,s.length()-4);
    }
}
