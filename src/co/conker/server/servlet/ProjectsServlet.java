package co.conker.server;

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

class ProjectsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		
		/*
		
		String latitude = request.getParameter("lat");
		String longitude = request.getParameter("long");
		
		Connection conn;
		JSONArray projectsArray;
		
		try {
			projectsArray = new JSONArray();
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(ConkerServer.dbURL, ConkerServer.dbUsername, ConkerServer.dbPassword);
				
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
				log("MySQL Error: " + e);
				System.exit(0);
			}
		} catch (JSONException e) {
			log("JSON Error.");
			System.exit(0);
		}
		
		conn = null;
		
		*/
		
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}

