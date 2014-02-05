package co.conker.servlet;

import co.conker.Database;
import co.conker.FileStorage;
import co.conker.entity.User;
import co.conker.entity.UserImage;
import co.conker.request.RegisterRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class RegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		RegisterRequest registerRequest = new RegisterRequest(request);

		if (registerRequest.isValid()) {
			User user = registerRequest.getUser();
			UserImage userImage = registerRequest.getUserImage();
			
			Database db = new Database();
			db.addUser(user);
			db.addUserImage(user, userImage);
			
			FileStorage fs = new FileStorage();
			fs.addUserImage(userImage);
		}
		
		response.getWriter().println("user registered successfully");
		//response.getWriter().println("session=" + request.getSession(true).getId());
	}
}

