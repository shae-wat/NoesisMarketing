package com.noesis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noesis.webinar.WebinarConnector;
import com.noesis.webinar.WebinarData;
import com.noesis.webinar.WebinarUser;

@SuppressWarnings("serial")
public class WebinarWebAppServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Upcoming Webinars\n");
		WebinarConnector wc = new WebinarConnector();
		
		List<WebinarData> upcomingWebinars = wc.getUpcomingWebinars();
		WebinarData registrationTestWebinar = new WebinarData();
		for (WebinarData webinar:upcomingWebinars){
			resp.getWriter().println(webinar.getSubject());
			if (webinar.getSubject().equals("The ABC's of Shared Savings Agreements"))
				registrationTestWebinar = webinar;
		}
		
		WebinarUser shaelyn = new WebinarUser("shaelyn", "watson", "shaelynjoy@gmail.com");
		List<WebinarUser> regUsers = new ArrayList<WebinarUser>();
		regUsers.add(shaelyn);
		
		wc.registerUsers(registrationTestWebinar.getWebinarKey(), regUsers);
		
	}
}
