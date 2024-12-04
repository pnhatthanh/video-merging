package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static Connection connect;
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/videoDB","root","");
			return connect;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
