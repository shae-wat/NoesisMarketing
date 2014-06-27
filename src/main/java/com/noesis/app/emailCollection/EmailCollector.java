package com.noesis.app.emailCollection;

import java.util.ArrayList;
import java.util.List;

import com.noesis.app.Utils;

public class EmailCollector {
	
	public EmailCollector() {
		
	}
	
	public static void main(String args[]){
		EmailCollector ec = new EmailCollector();
		//ec.collectPDF();
		ec.collectHTML();
		//ec.jsonLinkSearch();
		
	}
	
	public void collectPDF(){
		try {
			String pdfFile = "http://www.mbawpa.org/documents/TheGreenBuildersProfessionalDirectory_003.pdf";
			String pdfContent = Utils.pullPDFTextFromURL(pdfFile);
			//System.out.println("pdf = " + pdfContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
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
			String url = "http://www.ases.org/local/";
			String htmlContent = Utils.readFromURL(url);
			System.out.println("web = " + htmlContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(htmlContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
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
		String jsonFile = "greenBuilding.json";
		List<String> links = Utils.jsonLinkExtractor(jsonFile);
		System.out.println("EMAILS: \n");
		try{
			for (String url : links){
				//System.out.println(url);
				String htmlContent = Utils.readFromURL(url);
				List<String> emails = Utils.pullEmailAddressesFromString(htmlContent);
				for (String email : emails){
					if (!email.equals("events@informacanada.com"))
						System.out.println(email);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
