package co.conker.server.request;

import co.conker.server.Database;
import co.conker.server.entity.User;
import co.conker.server.entity.Project;
import co.conker.server.entity.ProjectImage;
import co.conker.server.util.Date;
import co.conker.server.util.FormUtils;
import co.conker.server.util.Geolocation;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class PostProjectRequest {
	// User
	private String title;
	private String description;
	private String noVolunteersNeeded;
	private String locationLat;
	private String locationLong;
	private String startDay;
	private String startMonth;
	private String startYear;
	private String endDay;
	private String endMonth;
	private String endYear;
	private int userID;
	
	// ProjectImage
	private Part image;
	
	private Project project;
	private ProjectImage projectImage;

	public PostProjectRequest(HttpServletRequest request) {
	
		try {
		
			title = FormUtils.partToString(request.getPart("title"));
			description = FormUtils.partToString(request.getPart("description"));
			noVolunteersNeeded = FormUtils.partToString(request.getPart("noVolunteersNeeded"));
			locationLat = FormUtils.partToString(request.getPart("locationLat"));
			locationLong = FormUtils.partToString(request.getPart("locationLong"));
			startDay = FormUtils.partToString(request.getPart("startDay"));
			startMonth = FormUtils.partToString(request.getPart("startMonth"));
			startYear = FormUtils.partToString(request.getPart("startYear"));
			endDay = FormUtils.partToString(request.getPart("endDay"));
			endMonth = FormUtils.partToString(request.getPart("endMonth"));
			endYear = FormUtils.partToString(request.getPart("endYear"));
			
			userID = ((User)request.getSession(false).getAttribute("User")).getID();

			image = request.getPart("image");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		if (isValid()) {
			// Project logic
			
			Geolocation location = new Geolocation(request.getParameter("locationLat"),
									   	   		   request.getParameter("locationLong"));
			
			Date start = new Date(request.getParameter("startDay"),
								  request.getParameter("startMonth"),
								  request.getParameter("startYear"));
								  
			Date end = new Date(request.getParameter("endDay"),
								request.getParameter("endMonth"),
								request.getParameter("endYear"));
								
			project = new Project(title, description, Integer.parseInt(noVolunteersNeeded), start, end, location, userID);
		
			// ProjectImage logic
			
			Database db = new Database();
			
			String source = getRandomHexString(32);
			while (!db.isProjectImageSourceAvailable(source)) {
				source = getRandomHexString(32);
			}
			
			projectImage = new ProjectImage(image, source);
		}
	}
	
	public boolean isValid() {
		return true;
	}
	
	public Project getProject() {
		return project;
	}
	
	public ProjectImage getProjectImage() {
		return projectImage;
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
