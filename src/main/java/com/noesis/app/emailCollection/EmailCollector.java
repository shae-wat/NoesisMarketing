package com.noesis.app.emailCollection;

import java.util.List;
import com.noesis.app.Utils;

public class EmailCollector {
	
	public EmailCollector() {
		
	}
	
	public static void main(String args[]){
		EmailCollector ec = new EmailCollector();
		ec.collect();
		
	}
	
	public void collect(){
		try {
			/* Code to take email addresses out of an email */
//			String pdfContent = Utils.pullPDFTextFromURL("http://www.kcpl.com/~/media/Files/Save%20Energy%20and%20Money/Missourirenewableresourcedirectory.pdf");
//			System.out.println("pdf = " + pdfContent);  //Sanity check
//			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			/* Code to take email addresses out of a HTML page */
			String url = "http://websafe.kemainc.com/ContractorMapreport/cemap/Reportpopup.aspx?ISDI=&TNAME=SouthEastern";
			String htmlContent = Utils.readFromURL(url);
			//System.out.println("web = " + htmlContent);  //Sanity check
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

}
