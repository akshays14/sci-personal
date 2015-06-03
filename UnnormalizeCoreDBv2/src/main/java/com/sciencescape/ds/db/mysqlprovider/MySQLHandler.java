package com.sciencescape.ds.db.mysqlprovider;

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
	 * @throws MySQLProviderException when db-server arguments are not expected
	 */
	public MySQLHandler(final String host, final int port,
			final String username, final String password,
			final String database) throws MySQLProviderException {
		/* verify inputs */
		if (host == null) {
			throw new MySQLProviderException(
					DBMSConstants.DatabaseConstants.DB_HOST_NULL_MESSAGE);
		}
		if (port <= 0) {
			throw new MySQLProviderException(
					DBMSConstants.DatabaseConstants.DB_PORT_INVALID_MESSAGE);
		}
		if (username == null) {
			throw new MySQLProviderException(
					DBMSConstants.DatabaseConstants.DB_USERNAME_NULL_MESSAGE);
		}
		if (password == null) {
			throw new MySQLProviderException(
					DBMSConstants.DatabaseConstants.DB_PASSWORD_NULL_MESSAGE);
		}
		if (database == null) {
			throw new MySQLProviderException(
					DBMSConstants.DatabaseConstants.DB_NAME_NULL_MESSAGE);
		}
		/* assign the values */
		this.dbHost = host;
		this.dbPort = port;
		this.dbUser = username;
		this.dbPassword = password;
		this.dbName = database;

		/**
		 * TODO: revisit if we want to keep this here in constructor
		 */
		// connect to database
		this.connect();
	}

	/**
	 * Connect method for {@link MySQLHandler}.
	 *
	 * @param autoCommit true to enable auto-commit mode
	 * @throws MySQLProviderException if not able to connect to MySQL server
	 */
	public final void connect(final boolean autoCommit)
			throws MySQLProviderException {
		try {
			try {
				Class.forName(DBMSConstants.MySQLHandlerConstants.
						JDBC_DRIVER_STRING);
			} catch (ClassNotFoundException ex) {
				throw new MySQLProviderException(DBMSConstants.
						MySQLHandlerConstants.JDBC_DRIVER_LOAD_ERROR);
			}
			String jdbcURL = makeJDBCurl();
			dbConnection = DriverManager.getConnection(jdbcURL, dbUser,
					dbPassword);
			dbConnection.setAutoCommit(autoCommit);
		} catch (SQLException ex) {
			throw new MySQLProviderException(ex);
		}
	}

	/**
	 * Connect method for {@link MySQLHandler}.
	 *
	 * @throws MySQLProviderException if not able to connect to MySQL server
	 */
	public final void connect() throws MySQLProviderException {
		this.connect(true);
	}

	/**
	 * close method for {@link MySQLHandler}.
	 *
	 * @throws MySQLProviderException if not able to close connection to DB
	 */
	public final void close() throws MySQLProviderException {
		try {
			this.dbConnection.close();
		} catch (SQLException ex) {
			throw new MySQLProviderException(ex);
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
		url.append(
				DBMSConstants.MySQLHandlerConstants.JDBC_HOST_PORT_SEPERATOR);
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

	/**
	 * Execute the given SQL statement.
	 *
	 * @param query SQL statement
	 * @return ResultSet table of data representing a database result set
	 * @throws MySQLProviderException as
	 */
	public final ResultSet executeQuery(final String query)
			throws MySQLProviderException {
		if (query == null) {
			throw new MySQLProviderException(
					DBMSConstants.MySQLHandlerOperations.QUERY_NULL_MESSAGE);
		}
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// prepare the statement
			statement = this.dbConnection.createStatement();
			// run the query
			resultSet = statement.executeQuery(query);
		} catch (SQLException ex) {
			throw new MySQLProviderException(ex);
		}
		return resultSet;
	}
}
