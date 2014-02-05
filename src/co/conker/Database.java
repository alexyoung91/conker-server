package co.conker;

import co.conker.entity.*;
import co.conker.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
	private static final String url = "jdbc:mysql://localhost:3306/Conker";
	private static final String username = "conker";
	private static final String password = "lolbanter2910";
	
	private Connection conn;
	
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	private void disconnect() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// USER RELATED
	
	public boolean addUser(User user) {
		connect();
		
		try {
		
			String query = "INSERT INTO User " +
						   "(id, email, firstName, lastName, gender, dob, organisation, homeLocationLat, homeLocationLong, password) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getGender());
			pstmt.setString(5, user.getDOB().getString());
			pstmt.setString(6, user.getOrganisation());
			pstmt.setString(7, String.valueOf(user.getHomeLocation().getLatitude()));
			pstmt.setString(8, String.valueOf(user.getHomeLocation().getLongitude()));
			pstmt.setString(9, user.getPasswordHash());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return true;
	}
	
	/*
	
	public User getUser(int id) {
	
	}
	
	*/
	
	public User getUser(String email) throws Exception {
		connect();
		
		User user = null;
		
		try {
		
			String query = "SELECT " +
						   "id, email, firstName, lastName, gender, dob, organisation, homeLocationLat, homeLocationLong, password " +
						   "FROM " +
						   "User " +
						   "WHERE " +
						   "email = ?;";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				user = new User(res.getString("firstName"),
								res.getString("lastName"),
								res.getString("email"),
								res.getString("gender"),
								new Date(res.getString("dob")),
								res.getString("organisation"),
								new Geolocation(res.getString("homeLocationLat"), res.getString("homeLocationLong")),
								res.getString("password"));
			} else {
				throw new Exception("UserDoesntExist");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return user;
	}
	
	/*
	
	// PROJECT RELATED
	
	public boolean addProject(Project project) {
	
	}
	
	public Project getProject() {
	
	}
	
	// SEARCH RELATED
	
	public boolean addSearch(Search search) {
	
	}
	
	*/
}

