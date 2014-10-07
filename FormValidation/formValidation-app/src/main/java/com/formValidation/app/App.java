package com.formValidation.app;

import java.io.IOException;
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
    	List<String> urlList = new ArrayList<String>();
    	boolean optInQ = false;
    	urlList.add("https://www.noesisenergy.com/site/content/energy-efficient-lighting-explained-0?");
    	urlList.add("https://www.noesisenergy.com/site/content/energy-project-finance-cheat-sheet");
    	
    	/*  How many fields are on the form?  */
    	for(String url : urlList){
    	 Document doc = Jsoup.connect(url).get();
         Elements inputs = doc.select("input");
         System.out.println("\n========\nForm url: " + url + "\n");
         for(Element input : inputs) {
             System.out.println(input.attr("ng-init"));
             if (input.attr("name").equals("Landing_Page_Opt_In__c")){
            	 optInQ = true;
             }
             
         }
         Elements otros = doc.select("p");
         for (Element p : otros){
        	 System.out.println(p.select("p[style=color:#FFFFFF]"));
         }
    	
    	
    	
    	/*  Does the form include the Google Analytics fields?  */
    	
    	/*  Does the page include the Google Analytics script?  */
    	
    	/*  What is the value of the hidden 'source' field?  */
    	
    	/*  Is there an 'opt in' question on the form? What is the text of the opt in question?  */
         System.out.println("\nIs there an 'opt in' question on the form? " + optInQ);
         
         
    	}
    	System.out.println( "\nForm Validator!" );
    }
}
