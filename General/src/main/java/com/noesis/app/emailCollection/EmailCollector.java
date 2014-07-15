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
			String pdfFile = "http://www.alliantenergy.com/wcm/groups/wcm_internet/@int/@ae/documents/application/mdaw/mdiz/~edisp/023496.pdf";
			String pdfContent = Utils.pullPDFTextFromURL(pdfFile);
			System.out.println("pdf = " + pdfContent);  //Sanity check
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
			String url = "http://www.pnmenergyefficiency.com/Projects/LinkClick.aspx?fileticket=fb019bAvXW8%3D&tabid=2058";
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
		String jsonFile = "franklinEnergy.json";
		List<String> links = Utils.jsonLinkExtractor(jsonFile);
		System.out.println("EMAILS: \n");
		try{
			for (String url : links){
				//System.out.println(url);
				//String htmlContent = Utils.readFromURL(url);
				List<String> emails = Utils.pullEmailAddressesFromString(url);
				for (String email : emails){
					if (!email.equals("info@dvgbc.org"))
						System.out.println(email);
				}
			}
			System.out.println("\nJSON done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

}
