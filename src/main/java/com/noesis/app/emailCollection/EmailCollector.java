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
		//ec.collectHTML();
		ec.jsonLinkSearch();
		
	}
	
	public void collectPDF(){
		try {
			String pdfFile = "http://www.xcelenergy.com/staticfiles/xe/Marketing/Managed%20Documents/north-trade-Account-Managers-List.pdf";
			String pdfContent = Utils.pullPDFTextFromURL(pdfFile);
			//System.out.println("pdf = " + pdfContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				System.out.println(email);
			}
			System.out.println("\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void collectHTML(){
		try{
			String url = "http://websafe.kemainc.com/ContractorMapreport/cemap/Reportpopup.aspx?ISDI=&TNAME=SouthEastern";
			String htmlContent = Utils.readFromURL(url);
			System.out.println("web = " + htmlContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(htmlContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				System.out.println(email);
			}
			System.out.println("\n");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void jsonLinkSearch(){
		List<String> emailList = new ArrayList<String>();
		String jsonFile = "necaLinks.json";
		List<String> links = Utils.jsonLinkExtractor(jsonFile);
		System.out.println("LINKS: \n");
		try{
			for (String url : links){
				System.out.println(url);
				String htmlContent = Utils.readFromURL(url);
				//System.out.println(htmlContent);
				emailList = Utils.trickyEmail(htmlContent);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
