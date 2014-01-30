package co.conker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
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
		
		context.addServlet(new ServletHolder(new DefaultServlet()),"/*");
        context.addServlet(new ServletHolder(new ProjectsServlet()), "/projects/*");
        context.addServlet(new ServletHolder(new ProjectImageServlet()),"/projectImage/*");
		 
        server.start();
        server.join();
	}
	
	public static void log(String msg) {
		System.out.println((new Date()).toString() + ": " + msg);
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu
