package sakuramoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

class DatabaseConnector {
	static Connection GetDatabaseConnection()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Properties p = new Properties();
			p.load(DatabaseConnector.class.getResourceAsStream("dbconn.properties"));
			Connection conn = DriverManager.getConnection(p.getProperty("connstr"), p.getProperty("user"),
					p.getProperty("password"));
			return conn;			
		} catch (Exception e) {
			throw new RuntimeException("Errors occured when connecting to database", e);
		}
	}
}
