package co.conker.server.servlet;

import co.conker.server.Database;
import co.conker.server.entity.User;
import co.conker.server.request.LoginRequest;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

public class LogoutServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);

		System.out.println("Logout request received");
		
		/*
		* get the current session but don't create one if one doesn't exist
		* if a session doesnt exist return status: false, if it does exist
		* and is successfully destory return status: true otherwise return
		* status false
		*/

		JSONObject jsonResponse = new JSONObject();

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
			jsonResponse.put("status", true);
		} else {
			jsonResponse.put("status", false);
		}
		
		response.getWriter().println(jsonResponse.toString());
	}
}

