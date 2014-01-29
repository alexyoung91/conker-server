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

	private Connection conn;
	private static String dbURL;
	private static String dbUsername;
	private static String dbPassword;

	public ConkerServer() {
		// Connect to MySQL
		conn = null;
		dbURL = "jdbc:mysql://localhost:3306/Conker";
		dbUsername = "conker";
		dbPassword = "lolbanter2910";
		
		try {
			conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (Exception e) {
			System.out.println("Failed to connect to MySQL! Error: " + e);
			System.exit(0);
		}
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
		
		Statement stmt = dbCon.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM User");
		
		String names = "";
		
		while(buildingsRS.next()) {
			//Integer id = buildingsRS.getInt("id");
			String firstName = res.getString("firstName");
			names += firstName + " ";
			//String hexcode = buildingsRS.getString("hexcode");
			//String description = buildingsRS.getString("description");
			//String centre_latitude = buildingsRS.getString("centre_latitude");
			//String centre_longitude = buildingsRS.getString("centre_longitude");
			//System.out.println("Building [Name: " + name + " | Hexcode: " + hexcode + " | Description: " + description + "]");
		}
		
        //response.getWriter().println("<h1>Hello World</h1>");
		response.getWriter().println(names);
    }
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        server.setHandler(new ConkerServer());
		 
        server.start();
        server.join();
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu