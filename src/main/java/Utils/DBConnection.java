package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/videodb","root","thanh1234");
			return connect;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
