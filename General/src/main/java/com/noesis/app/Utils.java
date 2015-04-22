package com.noesis.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {
	
	public static String readFromURL(String urlStr) throws Exception {
		// replace any spaces
		urlStr = urlStr.replace(" ", "%20");

		String result = "";
		URL url = new URL(urlStr);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			// System.out.println(inputLine);
			result = result + inputLine;
		}
		in.close();

		return result;
	}
	
	
	/* Under Development: Trying to find urls to emails within the html content */
	public static String pullUrlFromUrlString(String input){
		System.out.println("input = " + input);
		String desiredUrl = "";
		Matcher m = Pattern.compile("/main/email.aspx?did=[a-zA-Z0-9_.+-]&format=4").matcher(input);
	    while (m.find()) {
	    	desiredUrl = m.group();
	    	System.out.println("pullUrlFromUrlString= " + m.group());
	    }
	    return desiredUrl;
	}
	
	
	public static String pullPDFTextFromURL (String urlStr) throws Exception
	{
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument doc;
		URL url = new URL(urlStr);
		doc = PDDocument.load(urlStr);
		String content = stripper.getText(doc);
		return content;
	}
	
	
	public static String pullPDFTextFromFile (String fileStr) throws Exception
	{
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument doc;
		doc = PDDocument.load(fileStr);
		String content = stripper.getText(doc);
		return content;
	}
	
	
	public static String readTxtFile(String fileName) throws IOException {
		File fin = new File(fileName);
		FileInputStream fis = new FileInputStream(fin);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		String result = ".";
		while ((line = br.readLine()) != null) {
			result += line;
		}
		//System.out.println("result = \n" + result);
		br.close();
		return result;
	}
	
	public static boolean isEmail(String str){
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(str);
	    if (m.find())
	    	return true;
	    else
	    	return false;
	}
	
	public static List<String> pullEmailAddressesFromString (String input)
	{
		List<String> emails = new ArrayList<String>();
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(input);
	    while (m.find()) {
	    	if(!emails.contains(m.group())){
	    		emails.add(m.group());
	    	}
	    }
	    return emails;
	}
	
	
	public static List<String> pullTrickyEmailAddressesFromString (String input)
	{
		List<String> emails = new ArrayList<String>();
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+ \\+ @ \\+ [a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(input);
	    //System.out.println("Mgroups \n");
	    String[] e;
	    String email = "";
		while (m.find()) {
	    	if(!emails.contains(m.group())){
//	    		e = m.group().split("\\[at\\]");
//	    		email = e[0]+"@"+e[1];
//	    		//System.out.println("email0 = " + email);
//	    		e = email.split("\\[dot\\]");
//	    		email = e[0]+"."+e[1];
	    		emails.add(email);
	    		System.out.println(email);
	    	}
	    }
	    return emails;
	}
	
	
	public static String trickyEmail(String input)
	{
		List<String> emails = new ArrayList<String>();
		String email = "";
		//System.out.println("tricky Email input = " + input);
		try{
			email = input.substring(input.indexOf("strMailToAddress"), input.indexOf(";", input.indexOf("strMailToAddress"))).replace(" + ", "").replace("\"", "");
			email = email.substring(26);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	    return email;
	}
	
	
	public static List<String> jsonLinkExtractor(String jsonFile)
	{
		List<String> links = new ArrayList<String>();
		
		JSONParser parser = new JSONParser();
		try{
			Object obj = parser.parse(new FileReader(jsonFile));
			JSONObject jsonObject = (JSONObject) obj;
			//JSONObject results = (JSONObject) jsonObject.get("results");
			JSONArray collection = (JSONArray) jsonObject.get("collection1"); //.getClass("links").getClass("href");
			//System.out.println("collection array = " + collection);
			
			Iterator<JSONObject> iterator = collection.iterator();
			while (iterator.hasNext()) {
				JSONObject linkObject = iterator.next();
				JSONObject lObject = (JSONObject) linkObject.get("property1");
				String l = (String) lObject.get("href");
				//System.out.println(l);
				links.add(l);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		
		return links;
	}

}
