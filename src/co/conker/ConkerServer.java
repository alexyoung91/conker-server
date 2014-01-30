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

import java.util.Date;

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
	
		//log("-------------------------------");
		//log("URL: " + request.getRequestURL());
		//log("URI: " + request.getRequestURI());
		
		switch (request.getRequestURI()) {
			case "/projects": {
				String latitude = request.getParameter("lat");
				String longitude = request.getParameter("long");
				
				response.setContentType("application/json;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				baseRequest.setHandled(true);
				
				JSONArray projectsArray;
				
				try {
					projectsArray = new JSONArray();
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
					} catch (Exception e) {
						System.out.println("Failed to connect to MySQL! Error: " + e);
						System.exit(0);
					}
					
					try {
						// Join with user table??
						Statement stmt = conn.createStatement();
						ResultSet res = stmt.executeQuery("SELECT * FROM User, Project, ProjectPhoto WHERE User.id = Project.userID AND ProjectPhoto.projectID = Project.id ORDER BY Project.id ASC;");
						
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
							project.put("authorFirstName", res.getString("firstName"));
							project.put("authorLastName", res.getString("User.lastName"));
							project.put("photoSource", res.getString("source"));
							projectsArray.put(project);
						}
						
						stmt = null;
						res = null;
						
						JSONObject o = new JSONObject();
						o.put("projects", projectsArray);
						
						response.getWriter().println(o.toString(4));
						
					} catch (Exception e) {
						log("Failed to execute SQL query! Error: " + e);
						System.exit(0);
					}
				} catch (JSONException e) {
					log("JSON Error.");
					System.exit(0);
				}
				
				conn = null;
				
				log(latitude);
				break;
			}
			case "/projectImage": {
			
				response.setContentType("image/png");
				response.setStatus(HttpServletResponse.SC_OK);
				baseRequest.setHandled(true);
				
				
				
				break;
			}
		}
    }
	
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
        server.setHandler(new ConkerServer());
		 
        server.start();
        server.join();
	}
	
	public static void log(String msg) {
		System.out.println((new Date()).toString() + ": " + msg);
	}
}

// sudo update-alternatives --config java // to change java version on debian/ubuntu
