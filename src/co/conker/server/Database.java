/**
 * Database
 * 
 * This class communicates with the MySQL database
 */

package co.conker.server;

import co.conker.server.DBException;
import co.conker.server.entity.*;
import co.conker.server.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Database {
	private static final String url = "jdbc:mysql://localhost:3306/Conker";
	private static final String username = "conker";
	private static final String password = "qNMwUQ9XX5Yx7AMX";
	
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
	//
	// Returns user ID
	
	public int addUser(User user) {
		connect();
		
		int id = -1;
		try {
		
			String query = "INSERT INTO users " +
						   "(id, email, firstName, lastName, gender, dob, organisation, homeLocationLat, homeLocationLong, password) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getGender());
			pstmt.setString(5, user.getDOB().getString());
			pstmt.setString(6, user.getOrganisation());
			pstmt.setDouble(7, user.getHomeLocation().getLatitude());
			pstmt.setDouble(8, user.getHomeLocation().getLongitude());
			pstmt.setString(9, user.getPasswordHash());
			pstmt.executeUpdate();
			
			ResultSet res = pstmt.getGeneratedKeys();
			
			if (res.next()) {
        		id = res.getInt(1);
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return id;
	}
	
	public User getUser(int id) throws Exception {
		connect();
		
		User user = null;
		
		try {
		
			String query = "SELECT " +
						   "id, email, first_name, last_name, gender, dob, organisation, home_location_lat, home_location_long, password " +
						   "FROM " +
						   "users " +
						   "WHERE " +
						   "id = ?;";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, String.valueOf(id));
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				user = new User(res.getInt("id"),
								res.getString("first_name"),
								res.getString("last_name"),
								res.getString("email"),
								res.getString("gender"),
								new Date(res.getString("dob")),
								res.getString("organisation"),
								new Geolocation(res.getString("home_location_lat"), res.getString("home_location_long")),
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
	
	public User getUser(String email) throws DBException {
		connect();
		
		User user = null;
		
		try {
		
			String query = "SELECT " +
						   "id, email, first_name, last_name, gender, dob, organisation, home_location_lat, home_location_long, password " +
						   "FROM " +
						   "users " +
						   "WHERE " +
						   "email = ?;";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				user = new User(res.getInt("id"),
								res.getString("first_name"),
								res.getString("last_name"),
								res.getString("email"),
								res.getString("gender"),
								new Date(res.getString("dob")),
								res.getString("organisation"),
								new Geolocation(res.getString("home_location_lat"), res.getString("home_location_long")),
								res.getString("password"));
			} else {
				throw new DBException("UserDoesntExist");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return user;
	}
	
	public boolean isUserImageSourceAvailable(String source) {
		boolean isAvailable = true;
	
		connect();
		
		try {
		
			String query = "SELECT " +
						   "id " +
						   "FROM " +
						   "UserImage " +
						   "WHERE " +
						   "source = ?;";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, source);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				isAvailable = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return isAvailable;
	}
	
	public boolean addUserImage(UserImage userImage) {
		connect();
		
		try {
		
			String query = "INSERT INTO UserImage " +
						   "(id, source, userID) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userImage.getSource());
			pstmt.setInt(2, userImage.getUserID());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return true;
	}
	
	// PROJECT RELATED
	
	public int addProject(Project project) {
		connect();
		
		int id = -1;
		try {
		
			String query = "INSERT INTO Project " +
						   "(id, title, description, noVolunteersNeeded, startDate, endDate, locationLat, locationLong, userID) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, project.getTitle());
			pstmt.setString(2, project.getDescription());
			pstmt.setInt(3, project.getNoVolunteersNeeded());
			pstmt.setString(4, project.getStartDate().getString());
			pstmt.setString(5, project.getEndDate().getString());
			pstmt.setDouble(6, project.getLocation().getLatitude());
			pstmt.setDouble(7, project.getLocation().getLongitude());
			pstmt.setInt(8, project.getUserID());
			pstmt.executeUpdate();
			
			ResultSet res = pstmt.getGeneratedKeys();
			
			if (res.next()) {
        		id = res.getInt(1);
        	}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return id;
	}
	
	/*
	
	public Project getProject() {
		
	}
	
	*/
	
	public boolean isProjectImageSourceAvailable(String source) {
		boolean isAvailable = true;
	
		connect();
		
		try {
		
			String query = "SELECT " +
						   "id " +
						   "FROM " +
						   "ProjectImage " +
						   "WHERE " +
						   "source = ?;";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, source);
			ResultSet res = pstmt.executeQuery();
			
			if (res.next()) {
				isAvailable = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return isAvailable;
	}
	
	public boolean addProjectImage(ProjectImage projectImage) {
		connect();
		
		try {
		
			String query = "INSERT INTO ProjectImage " +
						   "(id, source, projectID) " +
						   "VALUES " +
						   "(DEFAULT, ?, ?);";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, projectImage.getSource());
			pstmt.setInt(2, projectImage.getProjectID());
			pstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		disconnect();
		
		return true;
	}
	
	// SEARCH RELATED
	
	/*
	
	public boolean addSearch(Search search) {
	
	}
	
	*/
}

