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
import org.json.JSONException;

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
		
		JSONArray projectsArray;
		
		try {
			projectsArray = new JSONArray();
			
			try {
				conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			} catch (Exception e) {
				System.out.println("Failed to connect to MySQL! Error: " + e);
				System.exit(0);
			}
			
			try {
				// Join with user table??
				Statement stmt = conn.createStatement();
				ResultSet res = stmt.executeQuery("SELECT * FROM Project ORDER BY id ASC;");
				
				while(res.next()) {
					JSONObject project = new JSONObject();
					project.put("title", res.getString("title"));
					project.put("description", res.getString("description"));
					project.put("noVolunteersNeeded", res.getInt("noVolunteersNeeded"));
					project.put("locationLat", res.getDouble("locationLat"));
					project.put("locationLong", res.getDouble("locationLong"));
					project.put("startDay", res.getInt("startDay"));
					project.put("startMonth", res.getInt("startMonth"));
					project.put("startYear", res.getInt("startYear"));
					project.put("endDay", res.getInt("endDay"));
					project.put("endMonth", res.getInt("endMonth"));
					project.put("endYear", res.getInt("endYear"));
					project.put("userID", res.getInt("userID"));
					projectsArray.put(project);
				}
				
				stmt = null;
				res = null;
				
				output = (new JSONObject().put("projects", projectsArray)).toString(4);
				
			} catch (Exception e) {
				System.out.println("Failed to execute SQL query! Error: " + e);
				System.exit(0);
			}
		} catch (JSONException e) {
			System.out.println("JSON Error.");
			System.exit(0);
		}
		
		conn = null;
		
		response.getWriter().println(output);
    }
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        server.setHandler(new ConkerServer());
		 
        server.start();
        server.join();
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu