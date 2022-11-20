package fr.sfc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database implements AutoCloseable {

	private Connection connection;
	private final Properties properties;
	private final String name;
	private String url;

	public Database(String name, Database.Properties properties) {
		this.name = name;
		this.properties = properties;
	}

	public void init() throws SQLException {
		this.url = "jdbc:" + DatabaseManager.dbFileProperties.getConfig().getDBMS() + "://" + properties.host + ":" + properties.port + "/" + name;
		this.connection = DriverManager.getConnection(url, properties.user, properties.password);
	}

	public Connection getConnection() {
		return connection;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}

	public static final class Properties {

		private final String host;
		private final String user;
		private final String port;
		private final String password;

		public Properties(final String host, final String user, final String port, final String password) {
			this.host = host;
			this.user = user;
			this.port = port;
			this.password = password;
		}

		public String getHost() {
			return host;
		}

		public String getUser() {
			return user;
		}

		public String getPort() {
			return port;
		}

		public String getPassword() {
			return password;
		}

	}

	public static final class Configuration {

		private final String driver;
		private final String dbms;

		public Configuration(String driver, String dbms) {
			this.driver = driver;
			this.dbms = dbms;
		}

		public String getDriver() {
			return driver;
		}

		public String getDBMS() {
			return dbms;
		}

	}

}