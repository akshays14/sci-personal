package com.sciencescape.ds.db.mysqlprovider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to handle all operations to MySQL.
 *
 * @author Akshay
 */
public class MySQLHandler {

	/**!< database hostname */
	private String dbHost = null;
	/**!< database port number*/
	private int dbPort;
	/**!< database username */
	private String dbUser = null;
	/**!< database password */
	private String dbPassword = null;
	/**!< name of the target database */
	private String dbName = null;
	/**!< connection object to DB */
	private Connection dbConnection;

	/**
	 * Get the connection object.
	 *
	 * @return SQL Connection object to the DB
	 */
	public final Connection getDbConnection() {
		return dbConnection;
	}

	/**
	 * Set the connection object.
	 *
	 * @param pDbConnection SQL Connection object to a DB
	 */
	public final void setDbConnection(final Connection pDbConnection) {
		this.dbConnection = pDbConnection;
	}

	/**
	 * Constructor for {@link MySQLHandler} class.
	 *
	 * @param host address of MySQL server
	 * @param port port of MySQL server
	 * @param username MySQL server user
	 * @param password MySQL user's password
	 * @param database name of the database
	 * @throws JDBCException
	 */
	public MySQLHandler(final String host, final int port,
			final String username, final String password,
			final String database) throws JDBCException {
		/* verify inputs */
		if (host == null) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_HOST_NULL_MESSAGE);
		}
		if (port <= 0) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_PORT_INVALID_MESSAGE);
		}
		if (username == null) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_USERNAME_NULL_MESSAGE);
		}
		if (password == null) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_PASSWORD_NULL_MESSAGE);
		}
		if (database == null) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_NAME_NULL_MESSAGE);
		}
		/* assign the values */
		dbHost = host;
		dbPort = port;
		dbUser = username;
		dbPassword = password;
		dbName = database;

		// connect to database
		try {
			connect();
		} catch (IOException e) {
			throw new JDBCException(e.getMessage());
		}
	}

	/**
	 * Connect method for MySQL
	 */
	public void connect() throws IOException {
		try {
			try {
				Class.forName(DBMSConstants.MySQLHandlerConstants.
						JDBC_DRIVER_STRING);
			} catch (ClassNotFoundException ex) {
				throw new IOException(DBMSConstants.MySQLHandlerConstants.
						JDBC_DRIVER_LOAD_ERROR);
			}
			String jdbcURL = makeJDBCurl();
			dbConnection = DriverManager.getConnection(jdbcURL, dbUser,
					dbPassword);
			dbConnection.setAutoCommit(true);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	/**
	 * close method for {@link MySQLHandler}
	 */
	public void close() throws IOException {
		try {
			dbConnection.close();
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	/**
	 * @brief makes JDBC url
	 * @return JDBC url string
	 *
	 * Method to make JDBC url, which is used to connect
	 * to MySQL server. It should be of the following form:
	 * jdbc:mysql://[host][:port]/[database][?property1][=value1]...
	 */
	private String makeJDBCurl() {
		StringBuilder url = new StringBuilder(DBMSConstants.
				MySQLHandlerConstants.JDBC_PROTOCOL_STRING);
		/* add db host */
		url.append(dbHost);
		url.append(DBMSConstants.MySQLHandlerConstants.JDBC_HOST_PORT_SEPERATOR);
		/* add db port */
		url.append(dbPort);
		url.append(DBMSConstants.MySQLHandlerConstants.JDBC_URL_DELIMITER);
		/* add database name */
		url.append(dbName);
		/* add zero date handling */
		url.append(DBMSConstants.MySQLHandlerConstants.ZERO_DATE_BEHAVIOR);
		/* convert to string and return */
		return (url.toString());
	}

	public ResultSet executeQuery(String query) throws IOException {
		if (query == null) {
			throw new IOException(DBMSConstants.MySQLHandlerOperations.QUERY_NULL_MESSAGE);
		}
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// prepare the statement
			statement = dbConnection.createStatement();
			// run the query
			resultSet = statement.executeQuery(query);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
		return resultSet;
	}
}
