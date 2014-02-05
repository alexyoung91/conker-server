package co.conker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// How to deal with invalid dates? Throw exception?

public class Date {
	private Calendar cal;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private void set(int day, int month, int year) {
		cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
	}
	
	private void set(String date) {
		Calendar cal  = Calendar.getInstance();
		
		try {
			cal.setTime(sdf.parse(date));
		} catch(ParseException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public Date(int day, int month, int year) {
		set(day, month, year);
	}
	
	public Date(String day, String month, String year) {
		set(Integer.parseInt(day),
			Integer.parseInt(month),
			Integer.parseInt(year));
	}
	
	public Date(String date) {
		set(date);
	}
	
	public int getDay() {
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getMonth() {
		return cal.get(Calendar.MONTH) + 1;
	}
	
	public int getYear() {
		return cal.get(Calendar.YEAR);
	}
	
	public String getString() {
		return sdf.format(cal.getTime());
	}
}

