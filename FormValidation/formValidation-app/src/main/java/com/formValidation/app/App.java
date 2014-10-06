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
    	urlList.add("https://www.noesisenergy.com/site/content/energy-efficient-lighting-explained-0?");
    	urlList.add("https://www.noesisenergy.com/site/content/energy-project-finance-cheat-sheet");
    	
    	for(String url : urlList){
    	 Document doc = Jsoup.connect(url).get();
         Elements inputs = doc.select("input");
         for(Element input : inputs) {
             System.out.println(input.attr("name"));
             System.out.println(input.attr("value"));
         }
    	}
    	 
    	System.out.println( "\nForm Validator!" );
    }
}
