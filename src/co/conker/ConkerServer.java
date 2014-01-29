package co.conker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConkerServer extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }
	
	@Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
    }
	
	public static void main(String[] args) throws Exception {
		System.out.println("DB Test");
		
		Server server = new Server(8080);
        server.setHandler(new ConkerServer());
		
		// Connect to MySQL
		//Connection dbCon = null;
		//String dbURL = "jdbc:mysql://localhost:3306/Conker";
		//String dbUsername = "conker";
		//String dbPassword = "lolbanter2910";

		/*
		try {
			dbCon = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (Exception e) {
			System.out.println("Failed to connect to MySQL! Error: " + e);
		}
		*/
		
		System.out.println("Connected OK.");
 
        server.start();
        server.join();
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu