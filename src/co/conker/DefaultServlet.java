package co.conker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class DefaultServlet extends HttpServlet {

	private static String pageContent = "";

	public DefaultServlet() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/home/ubuntu/ConkerResources/staticPages/ConkerForms.html"));
        try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null) {
				pageContent += line;
				line = br.readLine();
			}
        } finally {
			br.close();
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(pageContent);
		
		//response.getWriter().println("default");
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}