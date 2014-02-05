package co.conker.request;

import co.conker.Database;
import co.conker.entity.User;
import co.conker.util.Date;
import co.conker.util.Geolocation;

import org.mindrot.BCrypt;

import javax.servlet.http.HttpServletRequest;

public class RegisterRequest {
	private String firstName;
	private String lastName;
	private String email;
	private String gender;
	private String dobDay;
	private String dobMonth;
	private String dobYear;
	private String organisation;
	private String homeLocationLat;
	private String homeLocationLong;
	private String password;
	private String passwordConf;
	
	private User user;

	public RegisterRequest(HttpServletRequest request) {
		firstName = request.getParameter("firstName");
		lastName = request.getParameter("lastName");
		email = request.getParameter("email");
		gender = request.getParameter("gender");
		dobDay = request.getParameter("dobDay");
		dobMonth = request.getParameter("dobMonth");
		dobYear = request.getParameter("dobYear");
		organisation = request.getParameter("organisation");
		homeLocationLat = request.getParameter("homeLocationLat");
		homeLocationLong = request.getParameter("homeLocationLong");
		password = request.getParameter("password");
		passwordConf = request.getParameter("passwordConf");
		
		if (isValid()) {
			Date dob = new Date(request.getParameter("dobDay"),
						   		request.getParameter("dobMonth"),
						   		request.getParameter("dobYear"));
			
			Geolocation homeLocation = new Geolocation(request.getParameter("homeLocationLat"),
									   	   			   request.getParameter("homeLocationLong"));
			
			String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		
			user = new User(firstName, lastName, email, gender, dob, organisation, homeLocation, passwordHash);
		}
	}
	
	public boolean isValid() {
		return true;
	}
	
	public User getUser() {
		return user;
	}
}
