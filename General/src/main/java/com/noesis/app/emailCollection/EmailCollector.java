package com.noesis.app.emailCollection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.noesis.app.Utils;

public class EmailCollector {
	
	public EmailCollector() {
		
	}
	
	public static void main(String args[]){
		EmailCollector ec = new EmailCollector();
		//ec.collectPDF();
		//ec.collectHTML();
		ec.jsonLinkSearch();
		//ec.collectTxt();
		
	}
	
	public void collectTxt(){
		String longStr;
		try {
			longStr = Utils.readTxtFile("web.txt");
			//System.out.println("longStr=\n" + longStr);
			List<String> emailList = Utils.pullEmailAddressesFromString(longStr);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if(!email.equals("info@energytrust.org"))
					System.out.println(email);
			}
			System.out.println("\nTxt file done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void collectPDF(){
		try {
			String pdfFile = "web.pdf";
			String pdfContent = Utils.pullPDFTextFromURL(pdfFile);
			//System.out.println("pdf = " + pdfContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if(!email.equals("info@energytrust.org"))
					System.out.println(email);
			}
			System.out.println("\nPDF done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void collectHTML(){
		try{
			String url = "http://hvacreducation.net/xcel-co/public_search_proc.cfm";
			String htmlContent = Utils.readFromURL(url);
			System.out.println("web = " + htmlContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(htmlContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if (!email.equals("info@dvgbc.org"))
					System.out.println(email.toLowerCase());
			}
			System.out.println("\nHTML done");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jsonLinkSearch(){
		List<String> emailList = new ArrayList<String>();
		System.out.println("EMAILS: \n");
		for (int i=17; i<=20; i++){
			String jsonFile = "kimonoData ("+i+").json";
			//String jsonFile = "kimonoData.json";
			List<String> links = Utils.jsonLinkExtractor(jsonFile);
			
		
			for (String url : links){
				try{
				//System.out.println(url);
				String htmlContent = Utils.readFromURL(url);
				//System.out.println(htmlContent);
				List<String> emails = Utils.pullEmailAddressesFromString(htmlContent);
				for (String email : emails){
					if (!emailList.contains(email))
						emailList.add(email);
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//second iteration to remove duplicates
			for (String email : emailList){
				System.out.println(email);
			}
		} //end huge loop over json files
			System.out.println("\nJSON done");
	}

}
