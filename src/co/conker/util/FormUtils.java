package co.conker.util;

import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class FormUtils {
	public static String partToString(Part part) throws Exception {
		Scanner scanner = new Scanner(part.getInputStream());
		return scanner.nextLine();
	}
}
