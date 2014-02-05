package co.conker.request;

import co.conker.Database;
import co.conker.entity.User;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.mindrot.BCrypt;

import javax.servlet.http.HttpServletRequest;

public class LoginRequest {
	private String email;
	private String password;
	private boolean valid;
	private JSONObject jsonResponse;

	public LoginRequest(HttpServletRequest request) {
		email = request.getParameter("email");
		password = request.getParameter("password");
		
		jsonResponse = new JSONObject();
		
		if (email == null || email.isEmpty()) {
			jsonResponse.put("loggedin", false);
			jsonResponse.put("errormsg", "Please enter a username");
			return;
		}
		
		if (password == null || password.isEmpty()) {
			jsonResponse.put("loggedin", false);
			jsonResponse.put("errormsg", "Please enter a password");
			return;
		}
		
		Database db = new Database();
		
		User user;
		try {
			user = db.getUser(email);
			
			if (user.isPassword(password)) {
				// set "logged in" session variable
				jsonResponse.put("loggedin", true);
			} else {
				// confirms that user exists, privacy issues?
				jsonResponse.put("loggedin", false);
				jsonResponse.put("errormsg", "email/password mismatch");
			}
		} catch (Exception e) {
			switch (e.getMessage()) {
				case "UserDoesntExist": {
					jsonResponse.put("loggedin", false);
					jsonResponse.put("errormsg", "user doesn't exist");
					break;
				}
				
				default: {
					jsonResponse.put("loggedin", false);
					jsonResponse.put("errormsg", "unknown error");
					break;
				}
			}
		}
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getError() {
		return "";
	}
	
	public String getJSONResponse() {
		return jsonResponse.toString(4);
	}
}
