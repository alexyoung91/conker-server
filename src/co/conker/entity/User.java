package co.conker.entity;

import co.conker.util.Date;
import co.conker.util.Geolocation;
import javax.servlet.http.HttpServletRequest;
import org.mindrot.BCrypt;

public class User {
	private String 		firstName;
	private String 		lastName;
	private String 		email;
	private String 		gender;
	private Date   		dob;
	private String 		organisation;
	private Geolocation homeLocation;
	private String 		passwordHash;

	public User(String firstName, String lastName, String email, String gender, Date dob, String organisation, Geolocation homeLocation, String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		this.dob = dob;
		this.organisation = organisation;
		this.homeLocation = homeLocation;
		this.passwordHash = passwordHash;
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
