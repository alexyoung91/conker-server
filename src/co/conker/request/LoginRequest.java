package co.conker.request;

import co.conker.Database;
import co.conker.DBException;
import co.conker.entity.User;
import co.conker.util.RequestUtils;

import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.mindrot.BCrypt;

import javax.servlet.http.HttpServletRequest;

public class LoginRequest {
	
	private boolean valid;
	private JSONObject jsonResponse;
	
	private User user;

	public LoginRequest(HttpServletRequest request) {

		String email;
		String password;
		String body;
		JSONObject jsonRequest;
		Database db;

		valid = false;
		user = null;

		try {
			body = RequestUtils.getBody(request);

			jsonRequest = new JSONObject(body);

			email = jsonRequest.getString("email");
			password = jsonRequest.getString("password");

			System.out.println("Login request received: " + jsonRequest.toString());

			jsonResponse = new JSONObject();
	
			if (email == null || email.isEmpty()) {
				jsonResponse.put("status", false);
				jsonResponse.put("msg", "Please enter an email");
				return;
			}
			
			if (password == null || password.isEmpty()) {
				jsonResponse.put("status", false);
				jsonResponse.put("msg", "Please enter a password");
				return;
			}

			db = new Database();

			user = db.getUser(email);
			
			if (user.isPassword(password)) {
				// set "logged in" session variable
				jsonResponse.put("status", true);
				valid = true;
			} else {
				// confirms that user exists, privacy issues?
				jsonResponse.put("status", false);
				jsonResponse.put("msg", "Invalid email or password");
			}
		} catch (DBException e) {
			switch (e.getMessage()) {
				case "UserDoesntExist":
					jsonResponse.put("status", false);
					jsonResponse.put("msg", "Invalid email or password");
					break;
				default:
					jsonResponse.put("status", false);
					jsonResponse.put("msg", "Error logging in");
					break;
			}
		} catch (JSONException e) {
			System.out.println("json exception bein");
			return;
		} catch (IOException e) {
			System.out.println("io exception bein");
			return;
		}
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getError() {
		return "";
	}
	
	public String getJSONResponse() {
		return jsonResponse.toString(4);
	}
}
