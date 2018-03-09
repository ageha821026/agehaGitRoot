package testbed.unsafe;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JE11001 {
	private String getPassword() {
		return (new String("secret"));
	}

	public void exposeSecret() {
		String pw = getPassword();

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream("exposure.txt");
			fos.write(pw.getBytes());
		} catch (IOException e) {
			System.err.println("File close exception.");
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					System.err.println("File close exception.");
				}
		}
	}
}
