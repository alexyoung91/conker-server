package co.conker.entity;

import javax.servlet.http.Part;

public class UserImage {
	private Part 	part;
	private String 	source;
	private int 	userID;
	
	private boolean	userIDSet;

	public UserImage(Part part, String source) {
		this.part = part;
		this.source = source;
		
		userIDSet = false;
	}
	
	public boolean setUserID(int userID) {
		if (!userIDSet) {
			this.userID = userID;
			userIDSet = true;
			return true;
		}
		
		return false;
	}
	
	public Part getPart() {
		return part;
	}
	
	public String getSource() {
		return source;
	}
	
	public int getUserID() {
		return userID;
	}
}
