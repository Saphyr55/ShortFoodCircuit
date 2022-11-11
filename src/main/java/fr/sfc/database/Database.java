package fr.sfc.database;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class Database {

	private static Database database = null;
 	private final static HashMap<String, Connection> connections = new HashMap<>(10);
	private String url;
	private String password;
	private String user;
	private String port;
	private String dbName;

	public Database() {

	}


}