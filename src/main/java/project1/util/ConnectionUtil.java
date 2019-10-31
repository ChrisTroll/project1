package project1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() {
		String url = "jdbc:postgresql://revaturetraining.chvwilb5xwei.us-east-1.rds.amazonaws.com:5432/postgres";
		try {
			Connection conn = DriverManager.getConnection(
											url,
											System.getenv("EM_ROLE"),
											System.getenv("EM_PASS"));
			return conn;
		} catch (SQLException e){
			e.printStackTrace();
			System.out.println("Cannot connect to database.");
			return null;
		}
	}
}
