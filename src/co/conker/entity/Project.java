/**
 * Project
 *
 * Models a project as stored in the database
 */

package co.conker.entity;

import co.conker.util.Date;
import co.conker.util.Geolocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
public class Project {
	private int			id;
	private String 		title;
	private String 		description;
	private int    		noVolunteersNeeded;
	private Date   		start;
	private Date   		end;
	private Geolocation location;
	private int		  	userID;
	
	private boolean	idSet;
	
	public Project(int id, String title, String description, int noVolunteersNeeded, Date start, Date end, Geolocation location, int userID) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.noVolunteersNeeded = noVolunteersNeeded;
		this.start = start;
		this.end = end;
		this.location = location;
		this.userID = userID;
	}
	
	public Project(String title, String description, int noVolunteersNeeded, Date start, Date end, Geolocation location, int userID) {
		this.title = title;
		this.description = description;
		this.noVolunteersNeeded = noVolunteersNeeded;
		this.start = start;
		this.end = end;
		this.location = location;
		this.userID = userID;
	}
	
	public boolean setID(int id) {
		if (!idSet) {
			this.id = id;
			idSet = true;
			return true;
		}
		
		return false;
	}
	
	public int getID() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
 
	public int getNoVolunteersNeeded() {
		return noVolunteersNeeded;
	}
	
	public Geolocation getLocation() {
		return location;
	}
 
	public Date getStartDate() {
		return start;
	}
	
	public Date getEndDate() {
		return end;
	}
	
	public int getUserID() {
		return userID;
	}	
}
