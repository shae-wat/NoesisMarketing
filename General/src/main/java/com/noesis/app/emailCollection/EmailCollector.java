package com.noesis.app.emailCollection;

import java.util.ArrayList;
import java.util.List;

import com.noesis.app.Utils;

public class EmailCollector {
	
	public EmailCollector() {
		
	}
	
	public static void main(String args[]){
		EmailCollector ec = new EmailCollector();
		ec.collectPDF();
		//ec.collectHTML();
		//ec.jsonLinkSearch();
		
	}
	
	public void collectPDF(){
		try {
			String pdfFile = "nate.pdf";
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
			String url = "http://www.hvacradvice.com/site/324/About-NATE/Find-A-NATE-Certified-Technician";
			String htmlContent = Utils.readFromURL(url);
			System.out.println("web = " + htmlContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(htmlContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if (!email.equals("info@dvgbc.org"))
					System.out.println(email);
			}
			System.out.println("\nHTML done");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jsonLinkSearch(){
		List<String> emailList = new ArrayList<String>();
		String jsonFile = "kimonoData.json";
		List<String> links = Utils.jsonLinkExtractor(jsonFile);
		System.out.println("EMAILS: \n");
		
			for (String url : links){
				try{
				//System.out.println(url);
				String htmlContent = Utils.readFromURL(url);
				List<String> emails = Utils.pullEmailAddressesFromString(htmlContent);
				for (String email : emails){
					if (!email.endsWith(".gif") && !email.equals("xyz@abc.com"))
						System.out.println(email);
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("\nJSON done");
	}

}
