package com.noesis.app.emailCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

import com.noesis.app.Utils;

public class EmailCollector {
	
	public EmailCollector() {
		
	}
	
	public static void main(String args[]){
		EmailCollector ec = new EmailCollector();
		//ec.collectPDF("IESALC_2014_FC_Attendee_List.pdf", true);
		ec.collectDetailedCSV("IESALC_2014_FC_Attendee_List.pdf", true);
		//ec.collectHTML("http://www.micontrols.com/CONTACTUS.aspx");
		//ec.jsonLinkSearch();
		//ec.collectTxt();
	}
	
	
	public void collectPDF(String file, boolean isLocalFile){
		String pdfContent;
		try {
			if (isLocalFile)
				pdfContent = Utils.pullPDFTextFromFile(file);
			else
				pdfContent = Utils.pullPDFTextFromURL(file);
			//System.out.println("pdf = " + pdfContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if(!isCanadian(email))
					System.out.println(email);
			}
			System.out.println("\nPDF done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void collectDetailedCSV(String pdfFile, boolean isLocalFile){
		String pdfContent;
		try {
			if (isLocalFile)
				pdfContent = Utils.pullPDFTextFromFile(pdfFile);
			else
				pdfContent = Utils.pullPDFTextFromURL(pdfFile);
			//System.out.println("pdf = " + pdfContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(pdfContent);
			
			BufferedReader bufReader = new BufferedReader(new StringReader(pdfContent));
			String line=null;
			FileWriter writer = new FileWriter("iesConferenceAttendees.csv");
			int counter = 0;
			while( (line=bufReader.readLine()) != null )
			{
				if (counter > 1){
					line = line.replace(",", "");
					System.out.println("line = " + line);
					String[] info = line.split(" ");
					try
					{	for (int i = 0; i < info.length; i++){
							System.out.println("info["+i+"]= " + info[i]);
						}
						writer.append(info[0]+","+info[1]+",");
						for(int i=2; i<10; i++){
					    	try{
					    		
							    if(!Utils.isEmail(info[i])){
							    	writer.append(info[i] + " ");
							    }
							    else{
							    	writer.append("," + info[i]);
							    }
							   
					    	}
					    	catch (Exception ArrayIndexOutOfBoundsException){
					    		writer.append("");
					    	}
					    }
						

					    writer.append('\n');				 
					}
					catch(IOException e)
					{
					     e.printStackTrace();
					} 
				}
				counter++;
			}
			writer.flush();
		    writer.close();
			System.out.println("\nDetailed CSV done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void collectHTML(String url){
		System.out.println("EMAILS: \n");
//		String[] urlSuffixes = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT",
//								 "DE", "FL", "GA", "HI", "ID", "IL", "IN",
//								 "IA", "KS", "KY", "LA", "ME", "MD", "MA",
//								 "MI", "MN", "MS", "MO", "MT", "NE", "NV",
//								 "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
//								 "OK", "OR", "PA", "RI", "SC", "SD", "TN",
//								 "TX", "UT", "VT", "VA", "WA", "WV", "WI",
//								 "WY"
//								};
//		String[] urlSuffixes= {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
//							   "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
//							   "W", "X", "Y", "Z"
//							  };
		String[] states = {"Alabama", "Arkansas", "Arizona", "California",
						   "Connecticut", "Delaware", "Florida", "Georgia",
						   "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
						   "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
						   "Massaschusetts", "Mississippi", "Minnesota", "Missouri",
						   "Montana", "Michigan", "Nebraska", "Nevada", "New%20Hampshire",
						   "New%20Jersey", "New%20Mexico", "New%20York", "North%20Carolina", 
						   "North%20Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania",
						   "Rhode%20Island", "South Carolina", "South Dakota", "Tennessee",
						   "Texas", "utah", "Virgina", "Washington", "West%20Virginia",
						   "Wisconsin", "Wyoming"
						  };
//		for (int i = 1; i <= 50; i++){
		
		try{
			String htmlContent = Utils.readFromURL(url);
			//System.out.println("**" + s + " : url = " + url);
			//System.out.println("web = " + htmlContent);  //Sanity check
			List<String> emailList = Utils.pullEmailAddressesFromString(htmlContent);
			
			for (String email : emailList){
				if (!isCanadian(email))
					System.out.println(email.toLowerCase());
			}
			System.out.println("\nHTML done");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
//		System.out.println("\nHTML done");
	//}
	
	
	public void jsonLinkSearch(){
		List<String> emailList = new ArrayList<String>();
		System.out.println("EMAILS: \n");
		
		String jsonFile = "kimonoData.json";
		List<String> links = Utils.jsonLinkExtractor(jsonFile);
		
	
		for (String url : links){
			try{
			//System.out.println(url);
			String htmlContent = Utils.readFromURL(url);
			//Utils.pullUrlFromUrlString(htmlContent);
			//System.out.println(htmlContent);
			List<String> emails = Utils.pullEmailAddressesFromString(htmlContent);
			for (String email : emails){
				if (!emailList.contains(email) && !isCanadian(email) 
						&& !email.endsWith(".gif") && !email.endsWith(".jpg") 
						&& !email.equals("xyz@abc.com")){
					emailList.add(email);
					System.out.println(email);
				}
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
//		//second iteration to remove duplicates
		for (String email : emailList){
			System.out.println(email);
		}
		System.out.println("\nJSON done");
	}
	
	
	public void collectTxt(){
		String longStr;
		try {
			longStr = Utils.readTxtFile("web.txt");
			//System.out.println("longStr=\n" + longStr);
			List<String> emailList = Utils.pullEmailAddressesFromString(longStr);
			
			System.out.println("EMAILS: \n");
			for (String email : emailList){
				if(!isCanadian(email))
					System.out.println(email);
			}
			System.out.println("\nTxt file done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private boolean isCanadian(String email){
		if(email.endsWith(".ca")){
			return true;
		}
		return false;
	}

}
