package com.noesis.app.webinarProject;

import java.io.IOException;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/*
 * Methods to connect to GoToWebinar
 */

public class WebinarConnector {
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	public WebinarConnector() {
		try {
			Gson gson = new Gson();
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
			GenericUrl url = new GenericUrl("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
			
			HttpRequest request = requestFactory.buildGetRequest(url);
			HttpResponse response = request.execute();
			String content = response.parseAsString();
			
			WebinarAuth wa = gson.fromJson(content, WebinarAuth.class);
			System.out.println("GET = " + content);
			System.out.println("access token = " + wa.getAccess_token());
			System.out.println("organizer key = " + wa.getOrganizer_key());
			
			url = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/upcomingWebinars");
			request = requestFactory.buildGetRequest(url);
			HttpHeaders headers = new HttpHeaders();
			headers.setAuthorization("OAuth oauth_token=" + wa.getAccess_token());
			
			request.setHeaders(headers);
			response = request.execute();
			content = response.parseAsString();
			content = content.substring(2, (content.indexOf(",")));
			
			WebinarData wd = new WebinarData();
			wd.setWebinarKey(content.substring(content.indexOf(":")+1));
			System.out.println("\nwebinar key = " + wd.getWebinarKey());
			System.out.println("----------------------------------------");
			
			url = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() +"/webinars/" + wd.getWebinarKey() + "/registrants/fields");
			
			request = requestFactory.buildGetRequest(url);
			request.setHeaders(headers);
			response = request.execute();
			content = response.parseAsString();
			System.out.println("GET webinar fields= " + content);
			
			System.out.println("----------------------------------------");
			
//		Map<String, String> paramsMap = new HashMap<String, String>();
//		paramsMap.put("firstName", "Scott");
//		paramsMap.put("lastName", "Carter");
//		paramsMap.put("email", "scarter@noesis.com");
			
//		HttpRequest req = requestFactory.buildPostRequest(new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/871855095/registrants"), new UrlEncodedContent(paramsMap));
			
			WebinarUser wu = new WebinarUser("Shaelyn", "Watson", "shaelynjoy@gmail.com", "0000");
			//String registrationJSON = "{\"firstName\":"+ wu.getFirstName() +",\"lastName\":" + wu.getLastName() +",\"email\":"+ wu.getEmail() +"}";
			String registrationJSON = "{\"firstName\":\"Shaelyn\",\"lastName\":\"Watson\",\"email\":\"shaelynjoy@gmail.com\"}";
			byte[] b = registrationJSON.getBytes();
			
			HttpRequest req = requestFactory.buildPostRequest(new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/871855095/registrants"), new ByteArrayContent("application/json", b));
			
			req.setHeaders(headers);
			response = req.execute();  //try catch to avoid "user already registered"
			content = response.parseAsString();
			System.out.println("Registered user = " + content);
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//public WebinarAuth login(String email, String password)
		//kickoff
	
	//public List<WebinarData> getUpcomingWebinars (WebinarAuth auth)
	
	//public List<WebinarUser> getUsers(WebinarData wd)
	
	//public WebinarUser registerUser(List<WebinarUser>)
		//adds to hashmap, returns same type
	
	//public List<WebinarData> getHistoricalWebinars(WebinarAuth)
	
	//public WebinarUser getPollAnswers(WData)
		//populate poll results
	

}
