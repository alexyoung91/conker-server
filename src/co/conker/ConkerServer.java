/**
 * ConkerServer
 *
 * Main entry point to the application
 * 
 * This class is responsible for assigning HTTPRequest paths to servlets
 *
 */

package co.conker;

import co.conker.servlet.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.MultipartConfigElement;

import java.util.Date;
public class ConkerServer {
	public static final boolean debug = false;
	
	/**
	 * Application entry point
	 */

	public static void main(String[] args) throws Exception {
		
		// Create new Jetty server
		Server server = new Server(8080);
		
		// Register server context
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
        server.setHandler(context);
		
		// Display the development forms
		context.addServlet(new ServletHolder(new DefaultServlet()), "/*");
		
		// Login
		context.addServlet(new ServletHolder(new LoginServlet()), "/login/*");
		
		// Post a project
		ServletHolder sh = new ServletHolder(new RegisterServlet());
		sh.getRegistration().setMultipartConfig(new MultipartConfigElement("/home/ubuntu/Conker-Server/res/userImages", 1048576, 1048576, 262144));
		context.addServlet(sh, "/register/*");
		
		// Register
		ServletHolder sh2 = new ServletHolder(new PostProjectServlet());
		sh2.getRegistration().setMultipartConfig(new MultipartConfigElement("/home/ubuntu/Conker-Server/projectImages", 1048576, 1048576, 262144));
		context.addServlet(sh2, "/postProject/*");
		
		// Get projects nearest to a specified lat and long
        context.addServlet(new ServletHolder(new ProjectsServlet()), "/projects/*");
		
		// Get project image by specifying project ID
        context.addServlet(new ServletHolder(new GetProjectImageServlet()), "/projectImage/*");
        
        // Get user image by specifying project ID
        context.addServlet(new ServletHolder(new GetUserImageServlet()), "/userImage/*");
		 
        server.start();
        server.join();
	}
	
	/**
	 * print msg if debug mode is on
	 */
	
	public static void log(String msg) {
		System.out.println((new Date()).toString() + ": " + msg);
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu

