package co.conker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.MultipartConfigElement;
 
import java.io.IOException;
 
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

public class ConkerServer {
	public static final String dbURL = "jdbc:mysql://localhost:3306/Conker";
	public static final String dbUsername = "conker";
	public static final String dbPassword = "lolbanter2910";
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        //server.setHandler(new ConkerServer());
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
        server.setHandler(context);
		
		// Display the development forms
		context.addServlet(new ServletHolder(new DefaultServlet()),"/*");
		
		// Post a project
		ServletHolder sh = new ServletHolder(new ProjectPostServlet());
		sh.getRegistration().setMultipartConfig(new MultipartConfigElement("/home/ubuntu/ConkerResources/projectImages", 1048576, 1048576, 262144));
		context.addServlet(sh, "/projectPost/*");
		//context.addServlet(new ServletHolder(new ProjectPostServlet()), "/projectPost/*");
		
		// Get projects nearest to a specified lat and long
        context.addServlet(new ServletHolder(new ProjectsServlet()), "/projects/*");
		
		// Get project image by specifying project ID
        context.addServlet(new ServletHolder(new ProjectImageServlet()),"/projectImage/*");
		 
        server.start();
        server.join();
	}
	
	public static void log(String msg) {
		System.out.println((new Date()).toString() + ": " + msg);
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu
