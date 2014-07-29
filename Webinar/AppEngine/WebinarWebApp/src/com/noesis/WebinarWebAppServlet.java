package com.noesis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
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
		
		
		
//		wc.registerUser("802162383", shaelyn);
//		wc.registerUser("802162383", shae);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebinarConnector wc = new WebinarConnector();
		if (request.getParameter("First Name") != null) {
			System.out.println("!!!!!!!!!!!");
            //wc.registerUser(, user)
        }
		
	}
        
}
