package co.conker;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Date;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/*
@WebServlet("/ProjectPostServlet")
@MultipartConfig(location="home/ubuntu/ConkerResources/tmp",
				 fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100)      // 100 MB
*/

@WebServlet("/projectPost")
@MultipartConfig
public class ProjectPostServlet extends HttpServlet {
	//call post method 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
	
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		
		Part filePart = request.getPart("imageFile");

		InputStream in = filePart.getInputStream();
        OutputStream out = new FileOutputStream("/home/ubuntu/ConkerResources/projectImages/img.png");
        copy(in, out); //The function is below
        out.flush();
        out.close();
		
		response.getWriter().println("posted");
	}
	
	public static long copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer = new byte[4096];

		long count = 0L;
		int n = 0;

		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}