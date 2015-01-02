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

	public static String simpleHttpGet(String urlStr) throws Exception,
			IOException {
		Content c = Request.Get(urlStr).execute().returnContent();
		return c.toString();

	}

	public static String httpGet(String urlStr) throws IOException {
		String responseStr = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		// HttpGet httpGet = new HttpGet("http://targethost/homepage");
		HttpGet httpGet = new HttpGet(urlStr);
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			responseStr = EntityUtils.toString(response1.getEntity());
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}

		return responseStr;
	}

	public static String httpsGet(String https_url) {
		// String https_url = "https://www.google.com/";
		
		URL url;
		String responseStr = "";
		
		try {

			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			// pull returned content
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			String input;

			while ((input = br.readLine()) != null) {
				responseStr = responseStr + input;
			}
			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return responseStr;
	}


	public static String httpPost(String urlStr)
			throws ClientProtocolException, IOException {
		String responseStr = "";

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://targethost/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);

		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}

		return responseStr;
	}


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
	
	
	public static String pullPDFTextFromURL (String urlStr) throws Exception
	{
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument doc;
		//URL url = new URL(urlStr);
		doc = PDDocument.load(urlStr);
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
		Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+\\[at][a-zA-Z0-9-]+\\[dot][a-zA-Z0-9-.]+").matcher(input);
	    System.out.println("Mgroups \n");
	    String[] e;
	    String email = "";
		while (m.find()) {
	    	if(!emails.contains(m.group())){
	    		e = m.group().split("\\[at\\]");
	    		email = e[0]+"@"+e[1];
	    		//System.out.println("email0 = " + email);
	    		e = email.split("\\[dot\\]");
	    		email = e[0]+"."+e[1];
	    		emails.add(email);
	    		//System.out.println(email);
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
