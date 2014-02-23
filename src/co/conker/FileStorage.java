// Class created from modified version of: http://balusc.blogspot.co.uk/2007/04/imageservlet.html

/**
 * FileStorage
 * 
 * This class saves images to and retrieves images from disk storage
 */

package co.conker;

import co.conker.entity.*;
import co.conker.util.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;

public class FileStorage {
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	private static final String userImageDirectory = "/home/ubuntu/Conker-Server/res/userImages/";
	private static final String projectImageDirectory = "/home/ubuntu/Conker-Server/res/projectImages/";
	
	/**
	 * save user image to disk
	 */

	public void addUserImage(UserImage userImage) {
		String path = userImageDirectory + userImage.getSource() + ".png";
		System.out.println("writing image to: " + path);
		try {
			//userImage.getPart().write(path);
			
			InputStream in = userImage.getPart().getInputStream();
		    OutputStream out = new FileOutputStream(path);
		    copy(in, out, DEFAULT_BUFFER_SIZE); //The function is below
		    out.flush();
		    out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * save project image to disk
	 */
	
	public void addProjectImage(ProjectImage projectImage) {
		String path = projectImageDirectory + projectImage.getSource() + ".png";
		System.out.println("writing image to: " + path);
		try {
			//projectImage.getPart().write(path);
			
			InputStream in = projectImage.getPart().getInputStream();
		    OutputStream out = new FileOutputStream(path);
		    copy(in, out, DEFAULT_BUFFER_SIZE); //The function is below
		    out.flush();
		    out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * get user image from disk
	 */
	
	public File getUserImage(String source) throws IOException {
		// Decode the file name (might contain spaces and on) and prepare file object.
		return new File(userImageDirectory, URLDecoder.decode(source + ".png", "UTF-8"));
	}
	
	/**
	 * get project image from disk
	 */
	
	public File getProjectImage(String source) throws IOException {
		// Decode the file name (might contain spaces and on) and prepare file object.
		return new File(projectImageDirectory, URLDecoder.decode(source + ".png", "UTF-8"));
	}
	
	/**
	 * copy input stream to output stream
	 */
	
	public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];

		long count = 0L;
		int n = 0;

		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
