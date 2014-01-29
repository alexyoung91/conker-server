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

import org.json.JSONObject;
import org.json.JSONArray;

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
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
		
		String output = "";
		
		try {
			conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (Exception e) {
			System.out.println("Failed to connect to MySQL! Error: " + e);
			System.exit(0);
		}
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Projects;");
			
			String names = "";
			
			while(res.next()) {
				JSONObject o = new JSONObject();
				String title = res.getString("title");
				names += firstName + " ";
			}
			
			output = names;
		} catch (Exception e) {
			System.out.println("Failed to execute SQL query! Error: " + e);
			output = "SQL Error.";
		} catch (JSONException e) {
			System.out.println("Failed with JSON.");
			output = "JSON Error.";
		}
		
		conn = null;
		
		//response.getWriter().println(output);
		response.getWriter().println("{}");
    }
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        server.setHandler(new ConkerServer());
		 
        server.start();
        server.join();
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu