package co.conker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		// Connect to MySQL
		Connection dbCon = null;
		String dbURL = "jdbc:mysql://conker.co:3306/Conker";
		String dbUsername = "conker";
		String dbPassword = "lolbanter2910";

		try {
			dbCon = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
		} catch (Exception e) {
			System.out.println("Failed to connect to MySQL! Error: " + e);
		}
		
		System.out.println("Connected OK.");
	}
}
