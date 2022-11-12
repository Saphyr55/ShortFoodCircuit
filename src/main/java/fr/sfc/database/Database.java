package fr.sfc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Database implements AutoCloseable {

	private static final Map<String, Database> databases = new HashMap<>();
	private static final String mainDatabaseName = "sfc";
	private static String currentDbName = mainDatabaseName;
	private static final String CLASS_DRIVER = "com.mysql.cj.jdbc.Driver";
	static { initDriver(); }

	private final String ip = "127.0.0.1";
	private final String password = "";
	private final String user = "root";
	private final String port = "3306";
	private Connection connection;
	private String SGDBName;
	private String url;

	public Database(final String dbName) throws SQLException {
		SGDBName = CLASS_DRIVER.split("\\.")[1];
		url = "jdbc:" + SGDBName + "://" + ip + ":" + port + "/" + dbName;
		connection = DriverManager.getConnection(url, user, password);
	}

	public static void switchDB(String dbname) {
		currentDbName = dbname;
	}

	public static void switchMainDB() {
		currentDbName = mainDatabaseName;
	}

	public static Database get() {
		if (!databases.containsKey(currentDbName)) {
			try {
				databases.put(currentDbName, new Database(currentDbName));
			} catch (SQLException e) {
				e.printStackTrace();
				switchMainDB();
			}
		}
		return databases.get(currentDbName);
	}

	private static void initDriver() {
		try {
			Class.forName(CLASS_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static String getCurrentDbName() {
		return currentDbName;
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}

}