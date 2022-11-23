package fr.sfc.api.database;

import fr.sfc.api.database.impl.QueryBuilderImpl;
import fr.sfc.api.database.impl.QueryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database implements AutoCloseable {

	private Connection connection;
	private final Database.Properties properties;
	private final Database.Configuration configuration;
	private final String name;
	private String url;

	public Database(final String name, final Database.Configuration configuration, final Database.Properties properties) {
		this.name = name;
		this.configuration = configuration;
		this.properties = properties;
	}
	
	public void connect() throws SQLException {
		this.url = "jdbc:" + configuration.getDBMS() + "://" + properties.host + ":" + properties.port + "/" + name;
		this.connection = DriverManager.getConnection(url, properties.user, properties.password);
	}

	public Query createQuery(String request) {
		return new QueryImpl(connection, request);
	}

	public QueryBuilder createQueryBuilder() {
		return QueryBuilderImpl.of(connection);
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

	public static class Properties {

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