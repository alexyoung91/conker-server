package co.conker.request;

import co.conker.Database;
import co.conker.entity.User;
import co.conker.entity.UserImage;
import co.conker.util.Date;
import co.conker.util.Geolocation;
import co.conker.util.FormUtils;

import java.util.Random;

import org.mindrot.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class RegisterRequest {
	
	// User
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
	
	// UserImage
	private Part image;
	
	private User user;
	private UserImage userImage;

	public RegisterRequest(HttpServletRequest request) {
		
		try {
		
			firstName = FormUtils.partToString(request.getPart("firstName"));
			lastName = FormUtils.partToString(request.getPart("lastName"));
			email = FormUtils.partToString(request.getPart("email"));
			gender = FormUtils.partToString(request.getPart("gender"));
			dobDay = FormUtils.partToString(request.getPart("dobDay"));
			dobMonth = FormUtils.partToString(request.getPart("dobMonth"));
			dobYear = FormUtils.partToString(request.getPart("dobYear"));
			organisation = FormUtils.partToString(request.getPart("organisation"));			
			homeLocationLat = FormUtils.partToString(request.getPart("homeLocationLat"));
			homeLocationLong = FormUtils.partToString(request.getPart("homeLocationLong"));			
			password = FormUtils.partToString(request.getPart("password"));
			passwordConf = FormUtils.partToString(request.getPart("passwordConf"));

			image = request.getPart("image");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		if (isValid()) {
			
			// User logic
		
			Date dob = new Date(request.getParameter("dobDay"),
						   		request.getParameter("dobMonth"),
						   		request.getParameter("dobYear"));
			
			Geolocation homeLocation = new Geolocation(request.getParameter("homeLocationLat"),
									   	   			   request.getParameter("homeLocationLong"));
			
			String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
		
			user = new User(firstName, lastName, email, gender, dob, organisation, homeLocation, passwordHash);
			
			// UserImage logic
			
			Database db = new Database();
			
			String source = getRandomHexString(32);
			while (!db.isUserImageSourceAvailable(source)) {
				source = getRandomHexString(32);
			}
			
			userImage = new UserImage(image, source);
		}
	}
	
	public boolean isValid() {
		return true;
	}
	
	public User getUser() {
		return user;
	}
	
	public UserImage getUserImage() {
		return userImage;
	}
	
	private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }
}
