package co.conker.entity;

import co.conker.util.Date;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
public class Project {
	private String title;
	private String description;
	private int    noVolunteersNeeded;
	private Date   start;
	private Date   end;
	private double locationLat;
	private double locationLong;
	private User   author;
	
	/*
	
	public Project(HttpServletRequest request) {
		title = request.getParameter("title");
		description = request.getParameter("description");
		noVolunteersNeeded = Integer.parseInt(request.getParameter("noVolunteersNeeded"));
		
		start = new Date(request.getParameter("startDay"),
						  request.getParameter("startMonth"),
						  request.getParameter("startYear"));
		
		end = new Date(request.getParameter("endDay"),
						  request.getParameter("endMonth"),
						  request.getParameter("endYear"));
		
		locationLat = request.getParameter("locationLat");
		locationLong = request.getParameter("locationLong");
		
		userID = request.getParameter("userID");
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
	
	public int getLocationLat() {
		return locationLat;
	}
	
	public int getLocationLong() {
		return locationLong;
	}
 
	public Date getStartDate() {
		return start;
	}
	
	public Date getEndDate() {
		return end;
	}
	
	public User getUser() {
		return user;
	}
	
	*/
	
}
