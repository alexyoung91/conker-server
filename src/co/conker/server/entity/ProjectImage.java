/**
 * ProjectImage
 *
 * Models a project image as stored in the database
 */

package co.conker.server.entity;

import javax.servlet.http.Part;

public class ProjectImage {
	private Part 	part;
	private String 	source;
	private int 	projectID;
	
	private boolean	projectIDSet;

	public ProjectImage(Part part, String source) {
		this.part = part;
		this.source = source;
		
		projectIDSet = false;
	}
	
	public boolean setProjectID(int projectID) {
		if (!projectIDSet) {
			this.projectID = projectID;
			projectIDSet = true;
			return true;
		}
		
		return false;
	}
	
	public Part getPart() {
		return part;
	}
	
	public String getSource() {
		return source;
	}
	
	public int getProjectID() {
		return projectID;
	}
}
