package com.sciencescape.ds.db.mysqlprovider;

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
		public static final String ZERO_DATE_BEHAVIOR = "?zeroDateTimeBehavior=convertToNull";
	}

	public static class MySQLHandlerOperations {
		public static final String MYSQL_HANDLER_NULL_MESSAGE = "Given MySQLHandler object is null";
		public static final String MYSQL_CONNECTION_NULL_MESSAGE = "Given MySQL Connection object is null";
		public static final String QUERY_NULL_MESSAGE = "Given query is null";
		public static final String PMID_NULL_MESSAGE = "Given pm id is null";
		public static final String VENUE_ID_NULL_MESSAGE = "Given venue id is null";
		public static final String PAPER_ID_NULL_MESSAGE = "Given paper id is null";
	}

	public static class MySQLKeyWords {
		public static final String SELECT = "SELECT";
		public static final String FROM = "FROM";
		public static final String WHERE = "WHERE";
		public static final String SPACE = " ";
		public static final String ALL = "*";
		public static final String EQUALS = "=";
		public static final String LIMIT = "LIMIT";
		public static final String DOT = ".";
		public static final String COMMA = ",";
		public static final String INNER_JOIN = "INNER JOIN";
		public static final String ON = "ON";
		public static final String GREATER_THAN = ">";
		public static final String LESS_THAN_EQUAL_TO = "<=";
		public static final String AND = "AND";
		public static final String IS = "IS";
		public static final String NULL = "NULL";
	}
}
