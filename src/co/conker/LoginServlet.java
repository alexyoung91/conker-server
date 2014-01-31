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

import java.security.MessageDigest;

public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email == null || email.isEmpty()) {
			
		}
		
		if (password == null || password.isEmpty()) {
			
		}
		
		System.out.println("email: " + email + ", " + "password: " + password);
		
		Connection conn;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ConkerServer.dbURL, ConkerServer.dbUsername, ConkerServer.dbPassword);
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT email, password, salt FROM User WHERE email = ?");
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				String dbEmail = res.getString("email");
				String dbPassword = res.getString("password");
				String dbSalt = res.getString("salt");
				
				// Implement hashing n ting...
				
				// Hash the password
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				
				md.update(password.getBytes("UTF-8")); // Change this to "UTF-16" if needed
				byte[] digest = md.digest();
				String hash = new String(digest);
				
				MessageDigest md2 = MessageDigest.getInstance("SHA-256");
				
				hash = dbSalt + hash;
				md2.update(hash.getBytes("UTF-8"));
				byte[] digest2 = md2.digest();
				String hash2 = new String(digest2);
				
				System.out.println("Final hash: " + hash2);
				
				if (email.equals(dbEmail) && password.equals(dbPassword)) {
					response.getWriter().println("login done");
				} else {
					response.getWriter().println("login not done");
				}
			} else {
				response.getWriter().println("login not done");
			}
		} catch (Exception e) {
			log("MySQL Error: " + e);
			System.exit(0);
		}
		
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}