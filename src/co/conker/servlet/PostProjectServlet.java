package co.conker;

import co.conker.entity.Project;
import co.conker.entity.ProjectImage;
import co.conker.request.PostProjectRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
 
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class PostProjectServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		if (request.getSession(false) != null) {
			if ((boolean)request.getSession().getAttribute("LoggedIn")) {
				PostProjectRequest postProjectRequest = new PostProjectRequest(request);
				
				if (postProjectRequest.isValid()) {
					Project project = postProjectRequest.getProject();
					ProjectImage projectImage = postProjectRequest.getProjectImage();
			
					Database db = new Database();
					int projectID = db.addProject(project);
			
					projectImage.setProjectID(projectID);
					db.addProjectImage(projectImage);
			
					FileStorage fs = new FileStorage();
					fs.addProjectImage(projectImage);
				}
		
				response.getWriter().println("project posted sucessfully");
			} else {
				response.getWriter().println("not logged in");
			}
		} else {
			response.getWriter().println("not logged in");
		}
	}
}

