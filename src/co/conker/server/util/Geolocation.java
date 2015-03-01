package co.conker.server.util;

public class Geolocation {
	private double latitude;
	private double longitude;
	
	public Geolocation(double a_lat, double a_long) {
		set(a_lat, a_long);
	}
	
	public Geolocation(String a_lat, String a_long) {
		set(Double.parseDouble(a_lat),
			Double.parseDouble(a_long));
	}
	
	private void set(double a_lat, double a_long) {
		latitude = a_lat;
		longitude = a_long;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
