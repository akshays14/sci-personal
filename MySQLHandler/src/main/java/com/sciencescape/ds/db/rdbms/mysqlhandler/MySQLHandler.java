package main.java.com.sciencescape.ds.db.rdbms.mysqlhandler;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

import main.java.com.sciencescape.ds.db.rdbms.common.DataReader;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.common.DataWriter;
import main.java.com.sciencescape.ds.db.util.DBMSConstants;

public class MySQLHandler implements DataReader, DataWriter {

	private String _dbHost = null;		/*!< database hostname */
	private int _dbPort;				/*!< database port number*/
	private String _dbUser = null;		/*!< database username */
	private String _dbPassword = null;	/*!< database password */
	private String _dbName = null;		/*!< name of the target database */
	private String _tableName = null;	/*!< name of the target table */
	private String _cols[] = null;		/*!< array of column names */
	private Connection _dbConnection;	/*!< connection object to DB */
	
	public MySQLHandler(String host, int port, String username, String password,
			String database, String table, String cols[]) throws JDBCException {
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
		if (table == null) {
			throw new JDBCException(DBMSConstants.DatabaseConstants.DB_TABLE_NAME_NULL_MESSAGE);
		}
		
		_dbHost = host;
		_dbPort = port;
		_dbUser = username;
		_dbPassword = password;
		_dbName = database;
		_tableName = table;
		_cols = cols;
	}

	/**
	 * Connect method for MySQL
	 */
	@Override
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

	@Override
	public void close() throws IOException {
		try {
			_dbConnection.close();
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
	}

	@Override
	public void writeRecord(DataRecord rec) throws IOException {
		/**
		 * @todo to be implemented
		 */
	}

	@Override
	public DataRecord readRecord() throws IOException {
		ResultSet resultSet = null;
		if (resultSet == null) {
			//Create SQL statement, execute the query and store the resultset instance.
			StringBuilder strBuff = new StringBuilder("SELECT ");
			for (int i = 0; i < _cols.length - 1; i++) {
				strBuff.append(_cols[i]).append(",");
			}
			strBuff.append(_cols[_cols.length - 1]).append(" FROM ").append(_tableName);
			try {
				resultSet = _dbConnection.createStatement().executeQuery(strBuff.toString());
			} catch (SQLException ex) {
				throw new IOException(ex);
			}
		}
		/* fetch the record */
		try {
			if (!resultSet.next()) {
				return null;
			}

			Object data[] = new Object[_cols.length];
			for(int i=0;i<_cols.length;i++) {
				data[i] = resultSet.getObject(_cols[i]);
			}

			return new DataRecord(data);

		} catch (SQLException exp) {
			throw new IOException(exp);
		}
	}

	private String makeJDBCurl() {
		/**
		 * jdbc:mysql://[host][:port]/[database][?property1][=value1]...
		 * jdbc:mysql://localhost:3306/HerongDB?user=Herong&password=TopSecret
		 */
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
		url.append(DBMSConstants.MySQLHandlerConstants.JDBC_URL_QUERY_CHARACTER);
		/* convert to string and return */
		return (url.toString());
	}
}
