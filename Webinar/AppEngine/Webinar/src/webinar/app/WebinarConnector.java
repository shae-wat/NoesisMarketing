package webinar.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private static HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();
	private static HttpHeaders headers = new HttpHeaders();
	private static HttpRequest request;
	private static HttpResponse response;
	private static WebinarAuth wa;
	private static WebinarData wd;
	
	public WebinarConnector() {
		try {
			/* Authorize */
			Gson gson = new Gson();
			//Auth login
			GenericUrl url = new GenericUrl("https://api.citrixonline.com/oauth/access_token?grant_type=password&user_id=rallen@noesis.com&password=Austin2013&client_id=WUKeRBGxGEyH0gTEe2UG1ijANkaWL8Gy");
			
			request = requestFactory.buildGetRequest(url);
			response = request.execute();
			String content = response.parseAsString();
			
			wa = gson.fromJson(content, WebinarAuth.class);
			System.out.println("GET = " + content);
			System.out.println("access token = " + wa.getAccess_token());
			System.out.println("organizer key = " + wa.getOrganizer_key());
			
			headers.setAuthorization("OAuth oauth_token=" + wa.getAccess_token());
			

		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<WebinarUser> registerUsers(String webinarId,List<WebinarUser> listUsers){
		String content = "";
		String registerUrl = "https://api.citrixonline.com/G2W/rest/organizers/"  + wa.getOrganizer_key() + "/webinars/"+webinarId+"/registrants";

		System.out.println("GET webinar fields= " + content);
		
		for (WebinarUser wu:listUsers){
			try {
				//request = requestFactory.buildGetRequest(registerUrl);

				String registrationJSON = "{\"firstName\":\""+wu.getFirstName()+"\",\"lastName\":\""+wu.getLastName()+"\",\"email\":\""+wu.getEmail()+"\"}";
				byte[] b = registrationJSON.getBytes();
				HttpRequest req = requestFactory.buildPostRequest(new GenericUrl(registerUrl), new ByteArrayContent("application/json", b)); //build request
				
				req.setHeaders(headers);
				response = req.execute();
				content = response.parseAsString();
				
				System.out.println("Registered user = " + content);
				//Registered user = {"registrantKey":106478128,"joinUrl":"https://www4.gotomeeting.com/join/872916911/106478128"}
				//add to WebinarUser
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listUsers;
	}
	
	
	//public List<WebinarData> getUpcomingWebinars (WebinarAuth auth)
	
	
	//public List<WebinarUser> getUsers(WebinarData wd)
	
	//public List<WebinarData> getHistoricalWebinars(WebinarAuth)
	
	//public WebinarUser getPollAnswers(WData)
		//populate poll results
	
//	url = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() + "/upcomingWebinars");
//	request = requestFactory.buildGetRequest(url);

//	
//	request.setHeaders(headers);
//	response = request.execute();
//	content = response.parseAsString();
//	content = content.substring(2, (content.indexOf(",")));
//	
//	/* Fill WebinarData object */
//	wd = new WebinarData();
//	wd.setWebinarKey(content.substring(content.indexOf(":")+1));
//	System.out.println("\nwebinar key = " + wd.getWebinarKey());
//
//			
//	/* Prepare to register user and make call to register*/
//	GenericUrl webinarUrl = new GenericUrl("https://api.citrixonline.com/G2W/rest/organizers/" + wa.getOrganizer_key() +"/webinars/" + wd.getWebinarKey() + "/registrants/fields");
//	List<WebinarUser> users = new ArrayList<WebinarUser>();
//	WebinarUser wu = new WebinarUser("Shaelyn", "Watson", "shaelynjoy@gmail.com", "0000");
//	users.add(wu);
//	registerUsers(webinarUrl, users);
	

}
