package main.java.com.sciencescape.ds.db.util;

public class DBMSConstants {

	public static class DatabaseConstants {
		public static final String DB_HOST_NULL_MESSAGE = "Provided database host is null";
		public static final String DB_NAME_NULL_MESSAGE = "Provided database name is null";
		public static final String DB_USERNAME_NULL_MESSAGE = "Provided database username is null";
		public static final String DB_PASSWORD_NULL_MESSAGE = "Provided database password is null";
		public static final String DB_TABLE_NAME_NULL_MESSAGE = "Provided db table name is null";
		public static final String DB_PORT_INVALID_MESSAGE = "Provided database server port is invalid (<=0)";
	}

	public static class MySQLHandlerConstants {
		public static final String JDBC_DRIVER_STRING = "com.mysql.jdbc.Driver";
		public static final String JDBC_DRIVER_LOAD_ERROR = "Error loading JDBC driver";
		public static final String JDBC_PROTOCOL_STRING = "jdbc:mysql://";
		public static final String JDBC_URL_DELIMITER = "/";
		public static final String JDBC_HOST_PORT_SEPERATOR = ":";
		public static final String JDBC_URL_QUERY_CHARACTER = "?";
		public static final String JDBC_URL_QUERY_DELIMITER = "&";
		public static final String JDBC_URL_USER_STRING= "user";
		public static final String JDBC_URL_PASSWORD_STRING= "password";
		public static final String JDBC_URL_ASSIGNMENT_CHARACTER = "=";
	}
}