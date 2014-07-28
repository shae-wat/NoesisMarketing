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

		for (WebinarData webinar:upcomingWebinars){
			resp.getWriter().println(webinar.getSubject());
		}
		 
		WebinarUser shaelyn = new WebinarUser("shaelyn", "watson", "shaelynjoy@gmail.com");
		WebinarUser shae = new WebinarUser("shaelyn", "watson", "swatson@noesis.com");
		List<WebinarUser> regUsers = new ArrayList<WebinarUser>();
		regUsers.add(shaelyn);
		regUsers.add(shae);
		
		wc.registerUser("802162383", shaelyn);
		wc.registerUser("802162383", shae);
		
	}
}
