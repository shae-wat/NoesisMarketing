package com.noesis;

import java.io.IOException;

import javax.servlet.http.*;

import com.noesis.webinar.WebinarConnector;

@SuppressWarnings("serial")
public class WebinarWebAppServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		WebinarConnector wc = new WebinarConnector();
		
	}
}
