package co.conker.entity;

import co.conker.util.Date;
import co.conker.util.Geolocation;

import org.mindrot.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class User {
	private int			id;
	private String 		firstName;
	private String 		lastName;
	private String 		email;
	private String 		gender;
	private Date   		dob;
	private String 		organisation;
	private Geolocation homeLocation;
	private String 		passwordHash;
	
	private boolean	idSet;
	
	public User(int id, String firstName, String lastName, String email, String gender, Date dob, String organisation, Geolocation homeLocation, String passwordHash) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.organisation = organisation;
		this.homeLocation = homeLocation;
		this.passwordHash = passwordHash;
		
		idSet = true;
	}
	
	public User(String firstName, String lastName, String email, String gender, Date dob, String organisation, Geolocation homeLocation, String passwordHash) {
		this.id = -1;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.organisation = organisation;
		this.homeLocation = homeLocation;
		this.passwordHash = passwordHash;
		
		idSet = false;
	}
	
	public boolean setID(int id) {
		if (!idSet) {
			this.id = id;
			idSet = true;
			return true;
		}
		
		return false;
	}
	
	public int getID() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getGender() {
		return gender;
	}
	
	public Date getDOB() {
		return dob;
	}
	
	public String getOrganisation() {
		return organisation;
	}
	
	public Geolocation getHomeLocation() {
		return homeLocation;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public boolean isPassword(String plainTextCandidate) {
		return BCrypt.checkpw(plainTextCandidate, passwordHash);
	}
}
