package co.conker.entity;

import javax.servlet.http.Part;

public class UserImage {
	private Part part;
	private String source;

	public UserImage(Part part, String source) {
		this.part = part;
		this.source = source;
	}
	
	public Part getPart() {
		return part;
	}
	
	public String getSource() {
		return source;
	}
}
