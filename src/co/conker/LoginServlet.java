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
		
		Connection conn;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ConkerServer.dbURL, ConkerServer.dbUsername, ConkerServer.dbPassword);
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT email, password FROM User WHERE email = ?");
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				String dbEmail = res.getString("email");
				String dbPassword = res.getString("password");
				
				// Hash the password
				String hash = BCrypt.hashpw(password, BCrypt.gensalt());
				
				System.out.println("email: " + email);
				System.out.println("password: " + password);
				System.out.println("hashed password: " + hash);
				System.out.println("db hashed password: " + dbPassword);

				if (email.equals(dbEmail) && BCrypt.checkpw(dbPassword, password)) {
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

