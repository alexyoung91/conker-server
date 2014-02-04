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
import java.util.Calendar;

import java.text.SimpleDateFormat;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.mindrot.BCrypt;

public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		// How to use multipart: http://stackoverflow.com/questions/15729777/servlet-get-parameter-from-multipart-form-in-tomcat-7
		// The same may apply to servlets that are implemented using jetty...

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String dobDay = request.getParameter("dobDay");
		String dobMonth = request.getParameter("dobMonth");
		String dobYear = request.getParameter("dobYear");
		String organisation = request.getParameter("organisation");
		String homeLocationLat = request.getParameter("homeLocationLat");
		String homeLocationLong = request.getParameter("homeLocationLong");
		String password = request.getParameter("password");
		String passwordConf = request.getParameter("passwordConf");

		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(gender);
		System.out.println(dobDay);
		System.out.println(dobMonth);
		System.out.println(dobYear);
		System.out.println(organisation);
		System.out.println(homeLocationLat);
		System.out.println(homeLocationLong);
		System.out.println(password);
		System.out.println(passwordConf);

		// DO CHECKS

		// --

		// AFTER CHECKS

		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(dobYear), Integer.parseInt(dobMonth) - 1, Integer.parseInt(dobDay));
		//Date date = cal.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dob = sdf.format(cal.getTime());

		// Hash the password
		String hash = BCrypt.hashpw(password, BCrypt.gensalt());

		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(gender);
		System.out.println(dob);
		System.out.println(organisation);
		System.out.println(homeLocationLat);
		System.out.println(homeLocationLong);
		System.out.println(password + " - " + hash);

		Connection conn;
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ConkerServer.dbURL, ConkerServer.dbUsername, ConkerServer.dbPassword);
		
			String query = "INSERT INTO User " +
						   "(id, email, firstName, lastName, gender, dob, organisation, homeLocationLat, homeLocationLong, password) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, gender);
			pstmt.setString(5, dob);
			pstmt.setString(6, organisation);
			pstmt.setString(7, homeLocationLat);
			pstmt.setString(8, homeLocationLong);
			pstmt.setString(9, hash);

			pstmt.execute();
		} catch (Exception e) {
			log("MySQL Error: " + e);
			System.exit(0);
		}

		response.getWriter().println("default");
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}

