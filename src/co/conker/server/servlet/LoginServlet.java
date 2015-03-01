package co.conker.server.servlet;

import co.conker.server.Database;
import co.conker.server.entity.User;
import co.conker.server.request.LoginRequest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;

public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		
		LoginRequest loginRequest = new LoginRequest(request);
		
		HttpSession session;
		if (loginRequest.isValid()) {
			// Associate user with session?
			session = request.getSession(true);
			session.setAttribute("logged_in", true);
			session.setAttribute("user", loginRequest.getUser());
		}
		
		response.getWriter().println(loginRequest.getJSONResponse());
	}
}

