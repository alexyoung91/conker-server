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
			response.getWriter().println("please enter a username");
			return;
		}
		
		if (password == null || password.isEmpty()) {
			response.getWriter().println("please enter a password");
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
					response.getWriter().println("login successful");
				} else {
					response.getWriter().println("username/password mismatch"); // confirms that user exists, privacy issues?
				}
			} else {
				response.getWriter().println("user doesn't exist");
			}
		} catch (Exception e) {
			log("MySQL Error: " + e);
			System.exit(0);
			return;
		}
		
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}

