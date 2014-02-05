package co.conker;

import co.conker.entity.*;
import co.conker.util.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class FileStorage {
	private static String userImageDirectory = "/home/ubuntu/Conker-Server/res/userImages/";
	private static String projectImageDirectory = "/home/ubuntu/Conker-Server/res/projectImages/";

	public void addUserImage(UserImage userImage) {
		String path = userImageDirectory + userImage.getSource() + ".png";
		System.out.println("writing image to: " + path);
		try {
			//userImage.getPart().write(path);
			
			InputStream in = userImage.getPart().getInputStream();
		    OutputStream out = new FileOutputStream(path);
		    copy(in, out); //The function is below
		    out.flush();
		    out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void addProjectImage(ProjectImage projectImage) {
		String path = projectImageDirectory + projectImage.getSource() + ".png";
		System.out.println("writing image to: " + path);
		try {
			//projectImage.getPart().write(path);
			
			InputStream in = projectImage.getPart().getInputStream();
		    OutputStream out = new FileOutputStream(path);
		    copy(in, out); //The function is below
		    out.flush();
		    out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
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
