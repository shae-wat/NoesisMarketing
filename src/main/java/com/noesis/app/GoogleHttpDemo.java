package com.noesis.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Key;
import com.google.gson.Gson;
import com.noesis.app.webinarData.WebinarAuth;

public class GoogleHttpDemo {
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	public static void main (String args[])
	{
		GoogleHttpDemo ghd = new GoogleHttpDemo();
		try {
			ghd.demo();
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void demo () throws IOException
	{
		Gson gson = new Gson();
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
		GenericUrl url = new GenericUrl("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
		HttpRequest request = requestFactory.buildGetRequest(url);
		HttpResponse response = request.execute();
		String content = response.parseAsString();
		WebinarAuth wa = gson.fromJson(content, WebinarAuth.class);
		
		System.out.println(content);
		System.out.println(wa.getAccess_token());
		
		url = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/upcomingWebinars");
		request = requestFactory.buildGetRequest(url);
		HttpHeaders headers = new HttpHeaders();
		headers.setAuthorization("OAuth oauth_token=" + wa.getAccess_token());
		
		request.setHeaders(headers);
		response = request.execute();
		content = response.parseAsString();
		System.out.println("----------------------------------------");
		System.out.println(content);
		System.out.println("----------------------------------------");
		
		url = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() +"/webinars/871855095/registrants/fields");
		
		request = requestFactory.buildGetRequest(url);
		request.setHeaders(headers);
		response = request.execute();
		content = response.parseAsString();
		System.out.println(content);
		
		
		
		System.out.println("----------------------------------------");
		
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("firstName", "Scott");
//		paramsMap.put("lastName", "Carter");
//		paramsMap.put("email", "scarter@noesis.com");
		
//		HttpRequest req = requestFactory.buildPostRequest(new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/871855095/registrants"), new UrlEncodedContent(paramsMap));
		
		
		String registrationJSON = "{\"firstName\":\"Saumil\",\"lastName\":\"Jhaveri\",\"email\":\"test@test.com\"}";
		byte[] b = registrationJSON.getBytes();
		
		HttpRequest req = requestFactory.buildPostRequest(new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/871855095/registrants"), new ByteArrayContent("application/json", b));
		
		
		
		req.setHeaders(headers);
		response = req.execute();
		content = response.parseAsString();
		System.out.println(content);
	}
	
	
}
