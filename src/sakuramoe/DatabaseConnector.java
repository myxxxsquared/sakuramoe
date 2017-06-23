package sakuramoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnector {
	static Connection GetDatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Properties p = new Properties();
			p.load(DatabaseConnector.class.getResourceAsStream("dbconn.properties"));
			Connection conn = DriverManager.getConnection(p.getProperty("connstr"), p.getProperty("user"),
					p.getProperty("password"));
			try (Statement statement = conn.createStatement()) {
				statement.execute("SET NAMES utf8");
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("Errors occured when connecting to database", e);
		}
	}
}
