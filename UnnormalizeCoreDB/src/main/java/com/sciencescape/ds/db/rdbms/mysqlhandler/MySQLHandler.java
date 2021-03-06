package main.java.com.sciencescape.ds.db.rdbms.mysqlhandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;

import main.java.com.sciencescape.ds.db.util.DBMSConstants;

/**
 * @class MySQLHandler MySQLHandler.java
 * @brief class to handle all operations to MySQL.
 * @author Akshay
 *
 * Class to handle all operations to MySQL.
 */
public class MySQLHandler {

	private String _dbHost = null;		/*!< database hostname */
	private int _dbPort;				/*!< database port number*/
	private String _dbUser = null;		/*!< database username */
	private String _dbPassword = null;	/*!< database password */
	private String _dbName = null;		/*!< name of the target database */
	private Connection _dbConnection;	/*!< connection object to DB */

	public Connection get_dbConnection() {
		return _dbConnection;
	}

	public void set_dbConnection(Connection _dbConnection) {
		this._dbConnection = _dbConnection;
	}

	/**
	 * @brief constructor for {@link MySQLHandler}
	 * @param host address of MySQL server
	 * @param port port of MySQL server
	 * @param username MySQL server user
	 * @param password MySQL user's password
	 * @param database name of the database
	 * @param table name of the table
	 * @param cols column labels for the table
	 * @throws JDBCException
	 *
	 * Constructor for {@link MySQLHandler} class.
	 */
	public MySQLHandler(String host, int port, String username, String password,
			String database) throws JDBCException {
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
		_dbHost = host;
		_dbPort = port;
		_dbUser = username;
		_dbPassword = password;
		_dbName = database;

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
			_dbConnection = DriverManager.getConnection(jdbcURL, _dbUser,
					_dbPassword);
			_dbConnection.setAutoCommit(true);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	/**
	 * close method for {@link MySQLHandler}
	 */
	public void close() throws IOException {
		try {
			_dbConnection.close();
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
		url.append(_dbHost);
		url.append(DBMSConstants.MySQLHandlerConstants.JDBC_HOST_PORT_SEPERATOR);
		/* add db port */
		url.append(_dbPort);
		url.append(DBMSConstants.MySQLHandlerConstants.JDBC_URL_DELIMITER);
		/* add database name */
		url.append(_dbName);
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
			statement = _dbConnection.createStatement();
			// run the query
			resultSet = statement.executeQuery(query);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
		return resultSet;
	}
}