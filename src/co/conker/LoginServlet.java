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
import java.sql.PreparedStatement;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.mindrot.BCrypt;

public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);

		JSONObject jsonResponse = new JSONObject();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email == null || email.isEmpty()) {
			jsonResponse.put("loggedin", false);
			jsonResponse.put("errormsg", "Please enter a username");
			response.getWriter().println(jsonResponse.toString(4));
			return;
		}
		
		if (password == null || password.isEmpty()) {
			jsonResponse.put("loggedin", false);
			jsonResponse.put("errormsg", "Please enter a password");
			response.getWriter().println(jsonResponse.toString(4));
			return;
		}
		
		Connection conn;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ConkerServer.dbURL, ConkerServer.dbUsername, ConkerServer.dbPassword);
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT email, password FROM User WHERE email = ?");
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();

			// Should only return one result - duplicate emails should not exist
			if (res.next()) {
				String dbEmail = res.getString("email");
				String dbPassword = res.getString("password");

				if (email.equals(dbEmail) && BCrypt.checkpw(password, dbPassword)) {
					// set "logged in" session variable
					jsonResponse.put("loggedin", true);
					response.getWriter().println(jsonResponse.toString(4));
				} else {
					// confirms that user exists, privacy issues?
					jsonResponse.put("loggedin", false);
					jsonResponse.put("errormsg", "email/password mismatch");
					response.getWriter().println(jsonResponse.toString(4));
				}
			} else {
				jsonResponse.put("loggedin", false);
				jsonResponse.put("errormsg", "user doesn't exist");
				response.getWriter().println(jsonResponse.toString(4));
			}
		} catch (Exception e) {
			log("MySQL Error: " + e);
			jsonResponse.put("loggedin", false);
			jsonResponse.put("errormsg", "mysql error: " + e);
			response.getWriter().println(jsonResponse.toString(4));
			System.exit(0);
			return;
		}
		
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}

